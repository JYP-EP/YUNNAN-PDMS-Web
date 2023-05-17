<template>
  <div class="diagram-container">
    <div id="paper" class="page-div"></div>
    <virtual-and-reality ref="realityRef"></virtual-and-reality>
  </div>

</template>

<script>
import * as joint from 'jointjs';
import { logicWidth, logicHeight, btnWidth } from '@/api/jointjs/defineCb';
import { plateModel, plateOpenModel, plateWidth } from '@/api/jointjs/defineElement';
import * as circuit from '@/api/gridnt/circuit';
import * as circuitDrawJs from '@/api/gridnt/draw/circuit';
import * as commonDrawJs from '@/api/gridnt/draw/common';
import SvgPanZoom from 'svg-pan-zoom';
import VirtualAndReality from '@/views/gridnt/pomd/diagrams/circuit/virtualAndReality';
import { subscribe, unsubscribe } from '@/api/gridnt/websocket';
import _ from 'lodash';
import $ from 'jquery';

export default {
  name: 'CircuitDiagram',
  components: { VirtualAndReality },
  mounted() {
    this.initDatas();
  },
  created() {

  },
  props: {
    nodeType: {
      type: String
    },
    activeTab: {
      type: String
    }
  },
  watch: {
    nodeType: function(val) {
      if (val != 'I') {
        this.clearAll();
      }
    },
    activeTab: function(val) {
      if (val != 'circuit') {
        this.clearAll();
      }
    }
  },
  data() {
    return {
      mainIedInfo: {},
      bayInfo: {},
      subInfo: {},
      mainIedY: 50,
      iedWidth: 150,
      iedInitHeight: 100,
      pagePadding: 80,
      pageWidth: 0,
      logicMargin: 20,
      circuitHeight: 50,
      circuitMargin: 10,
      cbData: [],
      mainIed: null,
      paper: null,
      iedMargin: 10,
      plateXMargin: 15,
      svgPan: null,
      dialogVisible: false,
      graph: null,
      subsId: null, //控制块订阅
      circuitSubs: {}, //展开的虚回路信息订阅
      logicLineColor: ['green', 'red'],
      logicStatusDesc: ['正常', '中断'],
      lineCellMap: {},//需要刷新的Line  ,数据格式 {"k1":{"color":"#fff","cells":[cell1,cell2]}}
      textCellMap: {}, //需要刷新的Text
      plateCellMap: {}//需要刷新的软压板
    };

  },
  computed: {
    mainIedPosX: function() {
      return (this.pageWidth - this.iedWidth) / 2;
    },

    rightIedX: function() {
      return this.pageWidth - this.pagePadding - this.iedWidth;
    },
    //假设控制块和虚回路端点宽度一致，如果要修改，要改一下虚回路起始坐标计算
    circuitWidth: function() {
      return logicWidth;
    }
  },
  beforeDestroy() {
    this.clearAll();
  },
  methods: {
    clearAll() {
      this.graph.clear();
      this.lineCellMap = {};
      this.textCellMap = {};
      this.plateCellMap = {};
      if (this.svgPan != null) {
        this.svgPan.destroy();
        this.svgPan = null;
      }
      //退订控制块
      if (this.subsId != null) {
        unsubscribe(this.subsId);
        this.subsId = null;
      }
      //退订所有虚回路
      for (var f1065Code in this.circuitSubs) {
        unsubscribe(this.circuitSubs[f1065Code]);
        delete this.circuitSubs[f1065Code];
      }
      this.circuitSubs = {};
    },
    initDatas() {
      let self = this;
      self.graph = new joint.dia.Graph;
      self.paper = new joint.dia.Paper({
        el: document.getElementById('paper'),
        width: '100%',
        height: '100%',
        // gridSize: 20,
        model: self.graph,
        markAvailable: true,
        interactive: false
      });
      //添加虚回路展示收起监听 ，Line点击打开虚回路dialog
      this.handleListener();
    },
    /**
     *  根据左侧树的点击请求控制块数据
     */
    loadData(node) {
      this.clearAll();
      this.mainIedInfo = Object.assign({}, node);
      this.pageWidth = document.getElementById('paper').clientWidth;
      //画当前Ied
      this.drawMainIed();
      let self = this;
      //请求逻辑控制块
      circuit.loadCbList(this.mainIedInfo.id).then((resp) => {
        this.cbData = resp.data;
        console.log(this.cbData);
        if (this.cbData != null) {
          let subscirbeData = [];
          //画对端的Ied
          this.drawSideIed(0, subscirbeData);
          this.drawSideIed(1, subscirbeData);
          //画完所有的图之后，要把主Ied拉伸相应的高度
          commonDrawJs.ResizeMainIed(this.cbData, this.mainIedY, this.graph, this.mainIed);
          // 绑定 拖动 放缩插件
          this.svgPan = SvgPanZoom('#paper svg', {
            zoomEnabled: true,
            controlIconsEnabled: false,
            fit: false,
            center: false,
            dblClickZoomEnabled: false
          });
          //订阅
          self.subscribeData(subscirbeData, self.refreshLine);
        }
      });
    },
    /**
     * f1065Code判断订阅的是否是虚回路
     */
    subscribeData(subscirbeData, callback, f1065Code) {
      if (f1065Code != undefined) {
        if (this.circuitSubs[f1065Code] != undefined) {
          unsubscribe(this.circuitSubs[f1065Code]);
          delete this.circuitSubs[f1065Code];
        }
      } else {
        if (this.subsId != null && this.subsId != '') {
          //先取消订阅
          unsubscribe(this.subsId);
        }
      }
      if (subscirbeData.length > 0) {
        let paramId = Math.ceil((Math.random() * 100));
        //组装数据
        let paramData = {
          'method': 'subs',
          'params': subscirbeData,
          'id': paramId
        };
        let subsCmd = {
          subsParam: paramData,
          onData: callback
        };
        if (f1065Code != undefined) {
          this.circuitSubs[f1065Code] = 'svg' + paramId;
        } else {
          this.subsId = 'svg' + paramId;
        }
        subscribe(subsCmd);
      }
    },
    /**
     * 刷新svg数据
     * [{"key":"JSB001",table:"status", “value”: 1, “quality”: 0}]
     */
    refreshLine(dataList) {
      if (dataList.length > 0) {
        for (var i = 0; i < dataList.length; i++) {
          let refreshData = dataList[i];
          let key = refreshData['key'];
          let value = refreshData['value'];
          let newColor = this.logicLineColor[value];
          if (newColor != undefined) {
            if (this.lineCellMap[key] != undefined) {
              let currentColor = this.lineCellMap[key]['color'];
              if (newColor != currentColor) {
                this.lineCellMap[key]['color'] = newColor;
                let cells = this.lineCellMap[key]['cells'];
                if (cells != undefined) {
                  //刷新线的颜色
                  for (var j = 0; j < cells.length; j++) {
                    let cell = cells[j];
                    cell.attr('line/stroke', newColor);
                    let embeds = cell.getEmbeddedCells();
                    if (embeds.length > 0) {
                      for (var k = 0; k < embeds.length; k++) {
                        embeds[k].attr('line/stroke', newColor);
                      }
                    }
                  }
                  //刷新dialog中的状态描述
                  let spans = $('.' + key + '-dialog-cir-top > tspan');
                  if (spans.length > 0) {
                    let currentText = spans[0].innerHTML;
                    let newText = currentText.substring(0, currentText.lastIndexOf('：') + 1) + this.logicStatusDesc[value];
                    spans[0].innerHTML = newText;
                  }
                }
              }
            }
          }
        }
      }
    },
    /**
     * 刷新遥测遥信量
     */
    refreshTextAndPlate(dataList) {
      if (dataList.length > 0) {
        for (var i = 0; i < dataList.length; i++) {
          let refreshData = dataList[i];
          let key = refreshData['key'];
          let value = refreshData['value'];
          if (value != undefined) {
            if (this.textCellMap[key] != undefined) {
              let currentVal = this.textCellMap[key]['value'];
              //遥测遥信
              if (value != currentVal) {
                this.textCellMap[key]['value'] = value;
                let cells = this.value[key]['cells'];
                cells.attr('text/text', value);
              }
            } else if (this.plateCellMap[key] != undefined) {
              //软压板
              let currentVal = this.plateCellMap[key]['value'];
              if (value != currentVal) {
                this.plateCellMap[key]['value'] = value;
                let cell = this.plateCellMap[key]['cell'];
                if (cell != undefined) {
                  let parent = cell.getParentCell();
                  let newCell = null;
                  //重新画软压板，重新绑定关联关系
                  if (value == 1) {
                    newCell = new plateModel({
                      position: cell.position(),
                      size: cell.size()
                    });

                  } else if (value == 0) {
                    newCell = new plateOpenModel({
                      position: cell.position(),
                      size: cell.size()
                    });
                  }
                  if (newCell != null) {
                    this.graph.addCell(newCell);
                    this.plateCellMap[key]['cell'] = newCell;
                    if (parent != null) {
                      parent.embed(newCell);
                    }
                  }
                  cell.remove();
                }
              }
            }
          }
        }
      }
    },
    /**
     * 画当前Ied
     */
    drawMainIed() {
      let self = this;
      let mainIedDesc = this.mainIedInfo.label;
      let showDesc = joint.util.breakText(mainIedDesc, { width: this.iedWidth });
      self.mainIed = circuitDrawJs.drawIedCell(this.mainIedInfo.id, { x: self.mainIedPosX, y: self.mainIedY },
        { width: self.iedWidth, height: this.iedInitHeight }, showDesc);
      self.graph.addCell(self.mainIed);
    },
    /**
     * 画对端的Ied
     * @param index
     */
    drawSideIed(index, subDatas) {
      let sideIedData = this.cbData[index];
      if (sideIedData.length > 0) {
        let cells = [];
        for (let i = 0; i < sideIedData.length; i++) {
          //获取ied信息
          let item = sideIedData[i];
          let iedCode = item.iedInfo.f1046Code;
          let iedDesc = item.iedInfo.f1046Desc;
          let showDesc = joint.util.breakText(iedDesc, { width: this.iedWidth });
          let addHeight = (item['recv'] != undefined ? item['recv'].length : 0) + (item['send'] != undefined ? item['send'].length : 0);
          let height = this.iedInitHeight + addHeight * (logicHeight + this.logicMargin) + this.logicMargin;
          let position = {};
          let posY = i == 0 ? this.mainIedY : cells[i - 1].position().y + cells[i - 1].size().height + this.iedMargin;
          if (0 == index) {
            position = { x: this.pagePadding, y: posY };
          } else {
            position = { x: this.rightIedX, y: posY };
          }
          let subIed = circuitDrawJs.drawIedCell(iedCode, position,
            { width: this.iedWidth, height: height }, showDesc, index);
          cells.push(subIed);
          this.graph.addCell(subIed);
          //画逻辑控制块
          let logicCells = [];
          this.drawLogicBlocks(index, subIed, logicCells, item, 'recv', subDatas);
          this.drawLogicBlocks(index, subIed, logicCells, item, 'send', subDatas);

        }
      }
    },


    /**
     * 画控制块
     * @param index
     * @param iedCode
     * @param cbMap
     */
// recv ,send
    drawLogicBlocks(index, iedCell, logicCells, cbMap, sideType, subDatas) {
      let self = this;
      let logicList = cbMap[sideType];
      let targetCell = null;
      if (logicList != undefined) {
        //获取控制块起始地址
        let logicRectX = 0;
        if ('recv' == sideType) {
          logicRectX = this.mainIedPosX - logicWidth;
          if (index == 1) {
            logicRectX = this.mainIedPosX + this.iedWidth;
          }
          targetCell = iedCell;
        } else if ('send' == sideType) {
          logicRectX = this.pagePadding + this.iedWidth;
          if (index == 1) {
            logicRectX = this.rightIedX - logicWidth;
          }
          targetCell = this.mainIed;
        }
        for (let i = 0; i < logicList.length; i++) {
          let logicItem = logicList[i];
          let logicY = logicCells.length > 0 ? logicCells[logicCells.length - 1].position().y + this.logicMargin +
            logicHeight : iedCell.position().y + this.iedInitHeight / 2 * 0.8;
          let desc = this.getcbDesc(logicItem);
          let color = '001' == logicItem['F1065_TYPE'] ? '#ebbc19' : '#5c7aff';
          let position = { x: logicRectX, y: logicY };
          //控制块
          let cbView = circuitDrawJs.drawLogicBlockCell(logicItem, self, position, desc, color);
          logicCells.push(cbView);
          this.graph.addCell(cbView);
          let targetX = 0, targetY = cbView.position().y + (logicHeight + btnWidth / 2) / 2;
          if ('recv' == sideType) {
            targetX = this.pagePadding + this.iedWidth;
            if (index == 1) {
              targetX = this.rightIedX;
            }
          } else if ('send' == sideType) {
            targetX = this.mainIedPosX;
            if (index == 1) {
              targetX = this.mainIedPosX + this.iedWidth;
            }
          }
          let linePosition = { x: targetX, y: targetY };
          //控制块连线
          var l1 = circuitDrawJs.drawLogicLine(logicItem, cbView, linePosition);
          this.graph.addCell(l1);
          iedCell.embed([cbView, l1]);

          let statusCode = logicItem['F1065_STATUSCODE'];
          //组装订阅数据
          if (statusCode != undefined) {
            subDatas.push({ 'table': 'status', 'key': statusCode, 'cols': ['value'] });
            if (this.lineCellMap[statusCode] == undefined) {
              this.lineCellMap[statusCode] = {};
              this.lineCellMap[statusCode]['color'] = '#fff';
              this.lineCellMap[statusCode]['cells'] = [];
            }
            this.lineCellMap[statusCode]['cells'].push(l1);
          }
        }
      }
    },
    handleListener() {
      let self = this;
      //虚回路的展开收起
      self.paper.on('element:button:pointerdown', _.throttle(function(elementView, e) {
        e.stopPropagation();
        let eleId = elementView.model.get('viewType');
        if ('cbView' == eleId) {
          elementView.model.toggle();
        }

      }), 5000);
      //虚回路Dialog
      self.paper.on('link:pointerdown', _.throttle(function(elementView, e) {
        e.stopPropagation();
        let ele = elementView.model;
        let eleId = ele.get('viewType');
        let cbData = ele.get('cbData');
        if ('cbLine' == eleId) {
          // self.$nextTick(() =>{
          //查询当前链路状态值
          let statusCode = cbData['F1065_STATUSCODE'];
          let statusDesc = '数据无效';
          if (statusCode != undefined) {
            let color = self.lineCellMap[statusCode]['color'];
            let value = self.logicLineColor.indexOf(color);
            if (value > -1) {
              statusDesc = self.logicStatusDesc[value];
            }
          }
          self.$refs.realityRef.showDialog(self.subInfo, cbData, statusDesc);
          // })

        }
      }), 5000);
    },
    handleCBClick(cbData, collapsed) {
      let self = this;
      let tb1065Code = cbData['F1065_CODE'];
      //展开
      if (!collapsed) {
        //查询虚回路
        circuit.findGroupBy1065(tb1065Code).then((resp) => {
          let cirList = resp.data;
          if (cirList != null && cirList.length > 0) {
            let size = 0;
            cirList.forEach(item => size = size + item.list.length);
            let moveHight = this.circuitMargin * (size + 1) + this.circuitHeight * size;
            let subsList = [];
            this.drawCircuit(moveHight, cbData, cirList, subsList);
            //订阅
            self.subscribeData(subsList, this.refreshTextAndPlate, tb1065Code);
            this.moveOtherCells(cbData, moveHight);
          }
        });
      } else {
        //收起
        let containerCell = this.graph.getCell(tb1065Code + '-container');
        let moveHeight = containerCell.size().height;
        containerCell.remove();
        unsubscribe(this.circuitSubs[tb1065Code]);
        delete this.circuitSubs[tb1065Code];
        this.moveOtherCells(cbData, -moveHeight);
      }
    },
    /**
     *  展开虚回路之后需要移动其他元素的位置
     */
    moveOtherCells(cbData, moveHeight) {
      let tb1065Code = cbData['F1065_CODE'];
      let iedName = cbData['F1046_CODE_IEDRECV'];
      if (iedName == this.mainIedInfo.id) {
        iedName = cbData['F1046_CODE_IEDSEND'];
      }
      let iedCell = this.graph.getCell(iedName);
      iedCell.resize(iedCell.size().width, iedCell.size().height + moveHeight);
      let iedEmbeds = iedCell.getEmbeddedCells();
      let cbCell = this.graph.getCell(tb1065Code + '-rect');
      //当前Ied 关联的其他元素，再当前控制块下的都需要位移
      for (let i = 0; i < iedEmbeds.length; i++) {
        let sbItem = iedEmbeds[i];
        if (!sbItem.get('id').startsWith(tb1065Code)) {
          let type = sbItem.get('type');
          if (type == 'standard.Link') {
            if (sbItem.target().y > cbCell.position().y) {
              sbItem.translate(0, moveHeight);
            }
          } else {
            if (sbItem.position().y > cbCell.position().y) {
              sbItem.translate(0, moveHeight);
            }
          }
        }
      }
      //偏移当前Ied下的其他Ied
      let sideIndex = iedCell.get('sideIndex');
      let sameSideIed = this.cbData[sideIndex];
      for (let i = 0; i < sameSideIed.length; i++) {
        let sideIed = sameSideIed[i];
        let sideIedCode = sideIed.iedInfo.f1046Code;
        let sideIedCell = this.graph.getCell(sideIedCode);
        if (sideIedCell.position().y > iedCell.position().y) {
          sideIedCell.translate(0, moveHeight);
        }
      }
      commonDrawJs.ResizeMainIed(this.cbData, this.mainIedY, this.graph, this.mainIed);
    },
    /**
     * 画虚回路和软压板
     * @param allHeight
     * @param cbData
     * @param cirList
     */
    drawCircuit(allHeight, cbData, cirList, subsList) {
      let f1065Code = cbData['F1065_CODE'];
      //获取当前cb的坐标
      let logicCell = this.graph.getCell(f1065Code + '-rect');
      let sendRectX = logicCell.position().x;
      let sendRectY = logicCell.position().y + logicHeight + this.circuitMargin;
      let cbLine = this.graph.getCell(f1065Code + '-line');
      let lineColor = cbLine.attr('line/stroke');
      let cblineEndX = cbLine.target().x;
      let receiveX = 0, lineEndX = 0;
      if (sendRectX > cblineEndX) {
        receiveX = cblineEndX;
      } else {
        receiveX = cblineEndX - this.circuitWidth;
      }
      let containerCell = new joint.shapes.basic.Rect({
        id: f1065Code + '-container',
        size: { width: 0, height: allHeight }
      });
      this.graph.addCell(containerCell);
      logicCell.embed(containerCell);
      let cells = [];
      for (let i = 0; i < cirList.length; i++) {
        let cirInfo = cirList[i];
        let cirItem = cirInfo.list;
        //画虚回路
        for (let j = 0; j < cirItem.length; j++) {
          let cirData = cirItem[j];
          let posY = sendRectY + (this.circuitMargin + this.circuitHeight) * cells.length / 3;
          let desc = joint.util.breakText(cirData['f1061DescPsend'], { width: this.circuitWidth });
          let sendRect = new joint.shapes.basic.Rect({
            z: 10,
            position: { x: sendRectX, y: posY },
            size: { width: this.circuitWidth, height: this.circuitHeight },
            attrs: {
              text: { text: desc, fill: '#fff', fontSize: '13px', 'lengthAdjust': 'spacing' },
              rect: { stroke: '#3249b1', 'stroke-width': 0.5, fill: '#393a42', 'fill-opacity': '0.6' }
            }
          });
          desc = joint.util.breakText(cirData['f1062DescPrecv'], { width: this.circuitWidth });
          let receiveRect = sendRect.clone().position(receiveX, posY).prop('attrs/text/text', desc);
          let circuitLine = circuitDrawJs.drawCircuitLine(sendRect, receiveRect);
          circuitLine.attr('line/stroke', lineColor);
          cbLine.embed(circuitLine);
          cells.push(sendRect, receiveRect, circuitLine);
          containerCell.embed([sendRect, receiveRect, circuitLine]);
          //处理一下订阅数据
          this.packageSubsData(cirData, subsList, circuitLine);
        }
        //画软压板
        if (cirInfo.plate != undefined && (cirInfo.type == 'send' || cirInfo.type == 'recv')) {
          let sendCell = cells[cells.length - 3];
          let recvCell = cells[cells.length - 2];
          // let linkCell = cells[cells.length-1]
          let firstCell = cells[cells.length - (3 * cirItem.length)];
          let height = (cirItem.length - 1) * (this.circuitMargin + this.circuitHeight) + this.circuitHeight;
          let plateY = firstCell.position().y;
          let plateX = 0;
          if ('send' == cirInfo.type) {
            if (sendCell.position().x > recvCell.position().x) {
              plateX = sendCell.position().x - plateWidth - this.plateXMargin;
            } else {
              plateX = sendCell.position().x + this.circuitWidth + this.plateXMargin;
            }
          } else {
            if (sendCell.position().x > recvCell.position().x) {
              plateX = recvCell.position().x + this.circuitWidth + this.plateXMargin;
            } else {
              plateX = recvCell.position().x - plateWidth - this.plateXMargin;
            }
          }
          let plateCell = new plateModel({
            // id:cirInfo.plate,
            z: 12,
            position: { x: plateX, y: plateY },
            size: { height: height }
          });
          cells.push(plateCell);
          containerCell.embed(plateCell);
          subsList.push({ 'table': 'status', key: cirInfo.plate, cols: ['value'] });
          this.plateCellMap[cirInfo.plate] = { value: 1, cell: plateCell };
        }
      }
      this.graph.addCells(cells);
    },
    packageSubsData(cirData, subsList, circuitLine) {
      let statusCode = cirData['f1016CodeValue'];
      if (statusCode != undefined) {
        this.textCellMap[statusCode] = { 'value': 0, 'cells': circuitLine };
        let tableName = '';
        if (statusCode.indexOf('Analog') > -1) {
          tableName = 'analog';
        } else if (statusCode.indexOf('State') > -1) {
          tableName = 'status';
        }
        subsList.push({ 'table': tableName, 'key': statusCode, 'cols': ['value'] });
      }
    },
    getcbDesc(cbItem) {
      let type = cbItem['F1065_TYPE'];
      let cbCode = cbItem['F1065_APPID'];
      if (type == '001') {
        type = 'GS ';
      } else if (type == '002') {
        type = 'SV ';
      }
      let desc = type + cbCode;
      return desc;
    }
  }
};
</script>

<style scoped src="../../../../../assets/styles/gridnt/diagram.css">

</style>
