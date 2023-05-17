<template>
  <el-dialog width="70%" :visible.sync="dialogVisible" destroy-on-close @closed="clear">
    <div style="height: 700px;width: 100%;">
      <div id="virtuality-reality" class="diagram-dialog-div"></div>
    </div>
  </el-dialog>
</template>

<script>

import * as joint from 'jointjs';

import * as circuit from '@/api/gridnt/circuit';
import * as circuitAndReality from '@/api/gridnt/draw/circuitAndReality';
import {
  drawCircuit,
  drawSinglePhysical,
  drawMultiplePhysical,
  drawDialogItemTitle,
  titleHeight,
  addPhysicsSubsData
} from '@/api/gridnt/draw/dialogCommon';
import SvgPanZoom from 'svg-pan-zoom';
import { subscribe, unsubscribe } from '@/api/gridnt/websocket';
import $ from 'jquery';
import { plateModel, plateOpenModel } from '@/api/jointjs/defineElement';


export default {
  name: 'virtualAndReality',
  data() {
    return {
      cbData: null,
      physicalGraph: null,
      physicalPaper: null,
      dialogVisible: false,
      pagePadding: 60,
      realityTitleY: 50, // 默认Ied高度,实回路Title Y
      titleMarginBottom: 40,
      iedInitHeight: 50,
      pageWidth: 0,
      cirMarginTop: 80,
      cirRectHeight: 40,
      cirRectMargin: 10, //需要与 dialogCommon中的对应一致，否则ied高度不对
      physicalMarginTop: 20,
      physicalWidth: 200,
      physicalMargin: 150,
      svgPan: null,
      subsMap: {},
      subsId: null,
      lineStatusDesc: ['正常', '中断', '数据无效']
    };
  },
  computed: {
    iedY: function() {
      return this.pagePadding + titleHeight +10;
    }

  },
  mounted() {


  },
  methods: {
    clear() {
      if (this.physicalGraph != null) {
        this.physicalGraph.clear();
      }
      if (this.svgPan != null) {
        this.svgPan.destroy();
        this.svgPan = null;
      }
      if (this.subsId != null) {
        unsubscribe(this.subsId);
        this.subsId = null;
      }
      this.physicalPaper = null;
      this.physicalGraph = null;
      this.realityTitleY = this.iedY + this.iedInitHeight + this.physicalMarginTop;
      this.physicalMargin = 200;
      this.subsMap = {};
    },
    init() {
      let self = this;
      self.physicalGraph = new joint.dia.Graph;
      self.physicalPaper = new joint.dia.Paper({
        el: document.getElementById('virtuality-reality'),
        width: '100%',
        height: '100%',
        // gridSize: 20,
        model: self.physicalGraph,
        markAvailable: true,
        interactive: false
      });
    },
    showDialog(subs, cbData, statusDesc) {
      let self = this;
      self.cbData = Object.assign({}, cbData);
      this.clear();
      self.dialogVisible = true;
      self.$nextTick(() => {
        self.init();
        this.pageWidth = document.getElementById('virtuality-reality').clientWidth;
        //查询虚回路和物理回路信息
        circuit.findBy1065(self.cbData['F1065_CODE']).then((resp) => {
          // self.drawTitle(this.titleY, '二次回路（虚）');
          let cirList = resp.data.circuit;
          let size = 0;
          cirList.forEach(item => size = size + item.list.length);
          let addHeight = this.iedInitHeight + this.cirMarginTop + (this.cirRectHeight + this.cirRectMargin) * size;
          //订阅数据
          let subDatas = [];
          //画虚回路
          drawCircuit(this.pagePadding, this.pageWidth, self.cbData, cirList, addHeight,
            this.physicalGraph, statusDesc,this.cirRectHeight, subDatas, this.subsMap);
          this.realityTitleY = this.iedY + addHeight + this.physicalMarginTop;
          drawDialogItemTitle(new joint.shapes.standard.TextBlock(),
            this.pagePadding, this.realityTitleY, '物理回路（实）', this.physicalGraph);
          //物理回路
          self.drawReality(resp.data.reality, subDatas);
          self.subscribeData(subDatas);
          self.svgPan = SvgPanZoom('#virtuality-reality svg', {
            zoomEnabled: true,
            controlIconsEnabled: false,
            fit: false,
            center: false,
            dblClickZoomEnabled: false
          });
          // this.svgPan.zoomOut();
        });
      });
    },

    /**
     * 画物理回路
     * @param realityList
     */
    drawReality(realityList, subDatas) {
      //先判断ied的宽度
      let realityLen = realityList.length;
      let realityRectY = this.realityTitleY + this.titleMarginBottom;
      if (realityLen == 1) {
        let realityItem = realityList[0];
        //只有一条物理回路
        let cableCells = drawSinglePhysical(this.pageWidth, realityItem, realityRectY, this.physicalGraph);
        let link = circuitAndReality.drawLink(cableCells[0], cableCells[1],
          realityList[0]['f1051Name'] + ':' + realityList[0]['f1052No'], '状态：数据无效');
        //添加订阅信息
        addPhysicsSubsData(subDatas, this.subsMap, cableCells[0], cableCells[1], realityItem);
        if (realityItem['f1016Statuscode'] != undefined) {
          subDatas.push({ 'table': 'status', 'key': realityItem['f1016Statuscode'], cols: ['value'] });
          this.subsMap[realityItem['f1016Statuscode']] = { 'value': '--', 'cell': link };
        }
        this.physicalGraph.addCell(link);
      } else if (realityLen > 1) {
        //多条物理回路
        if (realityLen <= 3) {
          this.physicalMargin = (this.pageWidth - this.pagePadding * 2 - this.physicalWidth * (realityLen + 1)) / realityLen;
          let send1046Code = this.cbData['F1046_CODE_IEDSEND'];
          while (realityList.length > 0) {
            for (let i = 0; i < realityLen; i++) {
              let realityItem = realityList[i];
              if (realityItem['f1046CodeA'] == send1046Code) {
                realityList.splice(i, 1);
                send1046Code = realityItem['f1046CodeB'];
                drawMultiplePhysical(realityItem, realityRectY, this.physicalMargin, this.physicalGraph, subDatas, this.subsMap);
                break;
              }
            }
          }
        }
      }
    },
    /**
     * 订阅
     * @param subscirbeData
     * @param callback
     * @param f1065Code
     */
    subscribeData(subscirbeData) {
      if (this.subsId != null && this.subsId != '') {
        //先取消订阅
        unsubscribe(this.subsId);
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
          onData: this.reFreshDiagram
        };
        this.subsId = 'svg' + paramId;
        subscribe(subsCmd);
      }
    },
    /**
     * 刷新物理回路相关信息
     * @param dataList
     */
    reFreshDiagram(dataList) {
      for (var i = 0; i < dataList.length; i++) {
        let refreshData = dataList[i];
        let key = refreshData['key'];
        let value = refreshData['value'];
        if (this.subsMap[key] != undefined) {
          let currentValue = this.subsMap[key]['value'];
          if (value != currentValue) {
            this.subsMap[key]['value'] = value;
            let cell = this.subsMap[key]['cell'];
            let cellType = cell.get('type');
            if (cellType == 'standard.Link') {
              //链路状态
              let newText = '状态：' + this.lineStatusDesc[value];
              cell.label(1, {
                attrs: {
                  text: {
                    text: newText
                  }
                }
              });
            } else if (cellType == 'SoftOpenPlate' || cellType == 'SoftPlate') { //软压板
              let parent = cell.getParentCell();
              let newCell = null;
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
                newCell.prop('attrs/body/fill', '#003363').prop('attrs/body/stroke', '#3ca5bd').prop('attrs/circle1/stroke', '#3ca5bd').prop('attrs/circle2/stroke', '#3ca5bd')
                  .prop('attrs/line/stroke', '#3ca5bd').prop('attrs/line2/stroke', '#3ca5bd');
                this.physicalGraph.addCell(newCell);
                this.subsMap[key]['cell'] = newCell;
                if (parent != null) {
                  parent.embed(newCell);
                }
              }
              cell.remove();
            } else {
              //光强
              cell.prop('attrs/text2/text', '光强：' + value);
            }
          }
        }
      }
    }
  }
}
;
</script>

<style scoped>
/deep/ .marker-arrowheads {
  opacity: 0;
}

/deep/ .connection-wrap {
  stroke: #000000;
  stroke-width: 15;
  stroke-linecap: round;
  stroke-linejoin: round;
  opacity: 0;
}

/deep/ .link-tools {
  opacity: 0;
  /*cursor: pointer;*/
}

.diagram-dialog-div {
  background-color: #021334;
  width: 100%;
  height: 100%
}

</style>
