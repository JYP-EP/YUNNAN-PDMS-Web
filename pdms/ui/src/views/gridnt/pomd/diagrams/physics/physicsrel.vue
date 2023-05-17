<template>
  <el-dialog width='70%' :visible.sync='dialogVisible' destroy-on-close @closed='clear'>
    <div style='height: 700px;width: 100%;'>
      <div id='physics-rel' class='diagram-dialog-div'></div>
    </div>
  </el-dialog>
</template>

<script>
import * as joint from 'jointjs';
import { drawLink ,drawLogicLink} from '@/api/gridnt/draw/physics';
import * as physconnJs from '@/api/gridnt/physconn'
import * as circuit from '@/api/gridnt/circuit';
import {drawCircuit,drawSinglePhysical,drawDialogItemTitle, titleHeight,addPhysicsSubsData} from '@/api/gridnt/draw/dialogCommon'
import SvgPanZoom from 'svg-pan-zoom';
import * as circuitAndReality from '@/api/gridnt/draw/circuitAndReality';
import { subscribe, unsubscribe } from '@/api/gridnt/websocket';
import $ from 'jquery';
import { plateModel, plateOpenModel } from '@/api/jointjs/defineElement';

export default {
  name: 'Physicsrel',
  data() {
    return {
      dialogVisible: false,
      physicsData: null,
      physicalGraph: null,
      physicalPaper: null,
      svgPan: null,
      pageWidth: 0,
      pagePadding: 60,
      firstTitleY: 10,
      logicalList:[],
      logicRectHeight:30,
      logicRectWidth:100,
      logicRectMargin: 20,
      logicContainerTBPadding:5,
      currentLogicId:'',
      cirRectHeight: 40,
      cirRectMargin: 10, //需要与 dialogCommon中的对应一致，否则ied高度不对
      cirMarginTop: 150,
      cirIedInitHeight: 100,
      currentF1065Data:null,
      subsMap : {},
      subsList:[],
      subsId: null,
      cirsubsId:null,
      plateCellMap:{},
      physicsStatusDesc: ['正常', '中断', '数据无效'],
    };
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
      this.physicalPaper = null;
      this.physicalGraph = null;
      this.logicalList = []
      this.currentF1065Data = null
      this.subsMap = {};
      this.subsList = []
      this.plateCellMap = {}
      if(this.subsId != null){
        unsubscribe(this.subsId)
        this.subsId = null
      }
      if(this.cirsubsId != null){
        unsubscribe(this.cirsubsId)
        this.cirsubsId = null
      }
    },
    init() {
      let self = this;
      self.physicalGraph = new joint.dia.Graph;
      self.physicalPaper = new joint.dia.Paper({
        el: document.getElementById('physics-rel'),
        width: '100%',
        height: '100%',
        // gridSize: 20,
        model: self.physicalGraph,
        markAvailable: true,
        interactive: false
      });
      this.handleListener();
    },
    handleListener(){
      let self = this
      //缩放按钮
      self.physicalPaper.on('element:button:pointerdown', function(elementView, e) {
        e.stopPropagation();
        let model = elementView.model
        let viewType = model.get('viewType');
        if ('physicLine' == viewType) {
          self.changeExpendBtn(model)
        }
      });
      //点击逻辑控制块连线，展示虚回路
      self.physicalPaper.on('link:pointerdown', function(elementView, e) {
        e.stopPropagation();
        let model = elementView.model
        let viewType = model.get('viewType');
        if('logicLine' == viewType){
          if(self.currentLogicId != model.get("id")){
            self.currentLogicId = model.get("id")
            let cell = self.physicalGraph.getCell('logical-container')
            let embeds = cell.getEmbeddedCells()
            for(let i = 0;i<embeds.length;i++){
              embeds[i].findView(this).unhighlight()
            }
            elementView.highlight()
            model.getSourceCell().findView(this).highlight();
            model.getTargetCell().findView(this).highlight();
            if(self.currentF1065Data['F1065_CODE'] != model.get("viewData")['F1065_CODE']){
              if(self.cirsubsId != null){
                unsubscribe(self.cirsubsId)
                self.cirsubsId = null
              }
              self.currentF1065Data = model.get("viewData");
              self.loadCirData()
            }
          }
        }
      });
    },

    showDialog(subs, physicsData,statusDesc) {
      let self = this;
      self.physicsData = Object.assign({}, physicsData);
      self.clear();
      self.dialogVisible = true;
      self.$nextTick(() => {
        self.init();
        this.pageWidth = document.getElementById('physics-rel').clientWidth;
        drawDialogItemTitle(new joint.shapes.standard.TextBlock(),
          this.pagePadding, this.firstTitleY, '物理回路（实）', this.physicalGraph);
        let posY = this.firstTitleY + titleHeight;
        let cableCells = drawSinglePhysical(this.pageWidth,self.physicsData,posY,this.physicalGraph)
        addPhysicsSubsData(this.subsList, this.subsMap, cableCells[0], cableCells[1], self.physicsData);
         // 查询对应的逻辑链路
        physconnJs.loadLogical(this.physicsData['f1053Code']).then((resp) =>{
          this.logicalList = resp.data
          this.drawLogical()
          if(this.logicalList != null && this.logicalList.length > 0){
            for(var k = 0;k<this.logicalList.length;k++){
              let logical = this.logicalList[k]
              if(logical['F1065_STATUSCODE'] !=undefined){
                this.subsList.push({"table":"status","key":logical['F1065_STATUSCODE'],cols:['value']})
                this.subsMap[logical['F1065_STATUSCODE']] = {value:2}
              }
            }
            //有展开回收的链接线
            let physiclLineCell = drawLink(this.pageWidth, cableCells[0], cableCells[1],
              this.physicsData['f1051Name'] + ':' + this.physicsData['f1052No'], statusDesc);
            if(physicsData['f1016Statuscode'] != undefined){
              physiclLineCell.label(1,{
                attrs:{
                  text:{
                    class:physicsData['f1016Statuscode']+'-phy-status'
                  }
                }
              })
            }
            this.physicalGraph.addCell(physiclLineCell)
            self.currentF1065Data = this.logicalList[0]
            self.loadCirData()
          }else{
            //单链接线
            let link = circuitAndReality.drawLink(cableCells[0], cableCells[1],
              this.physicsData['f1051Name'] + ':' + this.physicsData['f1052No'], statusDesc);
            if(physicsData['f1016Statuscode'] != undefined){
              link.label(1,{
                attrs:{
                  text:{
                    class:physicsData['f1016Statuscode']+'-phy-status'
                  }
                }
              })
            }
            this.physicalGraph.addCell(link)
          }
          //订阅
          self.subscribeData()
        })
      });
    },

    /**
     * 点击物理链路链接线
     * @param model
     */
    changeExpendBtn(model){
      var buttonD;
      var collapsed = !model.get('collapsed') ;
      if (collapsed) {
        buttonD = 'M 2 7 12 7 M 7 2 7 12';
      } else {
        buttonD = 'M 2 7 12 7';
      }
      model.attr(['buttonIcon', 'd'], buttonD);
      model.set('collapsed', collapsed);
      this.showRelLogic(collapsed)
    },
    /**
     * 展示（收起）关联的逻辑链路
     */
    showRelLogic(collapsed){
      if(collapsed){
        //收起
        let cell = this.physicalGraph.getCell('logical-container')
        if(cell ){
          let cellHeight = cell.size().height
          cell.remove()
          this.resizeIedHeight(-cellHeight)
        }
      }else{
        this.drawLogical()
      }
    },
    /**
     * 画逻辑链路图
     */
    drawLogical(){
      let cableSendCell = this.physicalGraph.getCell('cable-'+this.physicsData['f1046CodeA']);
      let cableRecvCell = this.physicalGraph.getCell('cable-'+this.physicsData['f1046CodeB']);
      let startX = cableSendCell.position().x + cableSendCell.size().width
      let posY = cableSendCell.position().y+ cableSendCell.size().height/2
      let endX = cableRecvCell.position().x
      let cells = []
      if(this.logicalList != null && this.logicalList.length > 0){
        let height = this.logicalList.length * (this.logicRectHeight + this.logicRectMargin) + this.logicRectMargin + this.logicContainerTBPadding * 2
        let logicContainer = new joint.shapes.standard.Rectangle({
          z:3,
          id: 'logical-container',
          position:{x:startX,y:posY},
          size:{height:height,width:endX - startX},
          attrs:{
            body:{
              stroke : '#fff','stroke-width':'1',fill: '#003363'
            }
          }
        })
        this.physicalGraph.addCells(logicContainer)
        //画出所有的逻辑链路
        let logicStartY = posY + this.logicContainerTBPadding+ this.logicRectMargin
        for(let i = 0;i<this.logicalList.length;i++){
          let logical = this.logicalList[i]
          let posY = logicStartY+(this.logicRectHeight + this.logicRectMargin)*i
          let startCell =  new joint.shapes.standard.Rectangle({
            position:{x:startX,y:posY},
            size:{height:this.logicRectHeight,width:this.logicRectWidth},
            attrs:{
              body:{
                stroke : '#3ca5bd','stroke-width':'1',fill: '#003363'
              },
              label:{
                text: logical['F1046_NAME_IEDSEND'],stroke: '#3ca5bd', 'stroke-width': 1, fill: '#003363'
              }
            }
          })
          let endCellX = endX - this.logicRectWidth
          let endCell = startCell.clone().position(endCellX,posY).attr("label/text",logical['F1046_NAME_IEDRECV'])
          let logicalLineDesc = (logical['F1065_TYPE'] == '1' ?'GOOSE' : 'SV') +" APPID:"+logical['F1065_APPID']
          +" 状态：数据无效"
          let line = drawLogicLink(logical,startCell,endCell,logicalLineDesc);
          if(logical['F1065_STATUSCODE'] != undefined){
            line.label(0,{
              attrs:{
                text:{
                  class: logical['F1065_STATUSCODE']+'-logic-status'
                }
              }
            })
          }
          cells.push(startCell,endCell,line)
          logicContainer.embed([startCell,endCell,line])
        }
        this.physicalGraph.addCells(cells)
        this.resizeIedHeight(height)
        //高亮第一条
        for(let i = 0;i<=2;i++){
          cells[i].findView(this.physicalPaper).highlight()
        }
      }
    },
    resizeIedHeight(height){
      //修改两边ied的高度
      let sendCell = this.physicalGraph.getCell('rel-' + this.physicsData['f1046CodeA'])
      if(sendCell){
        sendCell.resize(sendCell.size().width,sendCell.size().height + height)
      }
      let receiveCell = this.physicalGraph.getCell('rel-' + this.physicsData['f1046CodeB'])
      if(receiveCell){
        receiveCell.resize(receiveCell.size().width,receiveCell.size().height + height)
      }
      //修改虚回路的Y
      let cirContainer = this.physicalGraph.getCell('cir-container')
      if(cirContainer != null){
        cirContainer.translate(0,height)
      }
    },

    loadCirData(){
      let self = this
      //默认选中第一条逻辑链路，展示对应的虚回路
      if(this.currentF1065Data != null){
          let sendCell = this.physicalGraph.getCell('rel-' + this.physicsData['f1046CodeA'])
          let titleY = sendCell.position().y+sendCell.size().height + 20
          let self = this
          let cirSubs = []
          circuit.findBy1065( this.currentF1065Data['F1065_CODE']).then((resp) => {
            let cirList = resp.data.circuit;
            let size = 0;
            cirList.forEach(item => size = size + item.list.length);
            let addHeight = this.cirIedInitHeight + this.cirMarginTop + (this.cirRectHeight + this.cirRectMargin) * size;
            let statusDesc = "数据无效"
            if(this.currentF1065Data['F1065_STATUSCODE'] != undefined){
              let statusKey = this.currentF1065Data['F1065_STATUSCODE']
              statusDesc = this.physicsStatusDesc[this.subsMap[statusKey]["value"]]
            }
            drawCircuit(titleY,self.pageWidth,self.currentF1065Data,cirList,addHeight,self.physicalGraph,
              statusDesc,this.cirRectHeight,cirSubs,this.plateCellMap)
            if(this.svgPan == null){
              this.svgPan = SvgPanZoom('#physics-rel svg', {
                zoomEnabled: true,
                controlIconsEnabled: false,
                fit: false,
                center: false,
                dblClickZoomEnabled: false
              });
              // this.svgPan.zoomOut();
            }
            if(cirSubs.length > 0){
              self.subscribePlate(cirSubs)
            }
          })
      }
    },
    /**
     * 订阅
     * @param subscirbeData
     * @param callback
     * @param f1065Code
     */
    subscribeData() {
      debugger
      if (this.subsId != null && this.subsId != '') {
        //先取消订阅
        unsubscribe(this.subsId);
      }
      if (this.subsList.length > 0) {
        let paramId = Math.ceil((Math.random() * 100));
        //组装数据
        let paramData = {
          'method': 'subs',
          'params': this.subsList,
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
    reFreshDiagram(dataList){
      for (var i = 0; i < dataList.length; i++) {
        let refreshData = dataList[i];
        let key = refreshData['key'];
        let value = refreshData['value'];
        if (this.subsMap[key] != undefined) {
          let currentValue = this.subsMap[key]['value'];
          if (value != currentValue) {
            this.subsMap[key]['value'] = value;
            let cell = this.subsMap[key]['cell']
            if(cell != undefined){
              //光强
              cell.prop('attrs/text2/text', '光强：'+value)
            }else{
              let spans = $("."+key+"-logic-status > tspan")
              if(spans.length > 0){
                let currentText = spans[0].innerHTML
                let newText = currentText.substring(0,currentText.lastIndexOf("：")+1)+this.physicsStatusDesc[value]
                spans[0].innerHTML = newText
              }
              spans = $("."+key+"-dialog-cir-top > tspan")
              if(spans.length > 0){
                let currentText = spans[0].innerHTML
                let newText = currentText.substring(0,currentText.lastIndexOf("：")+1)+this.physicsStatusDesc[value]
                spans[0].innerHTML = newText
              }
            }
          }
        }
      }
    },
    /**
     * 订阅 软压板
     * @param subscirbeData
     * @param callback
     * @param f1065Code
     */
    subscribePlate(cirSubsList) {
      if (this.cirsubsId != null && this.cirsubsId != '') {
        //先取消订阅
        unsubscribe(this.cirsubsId);
      }
      if (cirSubsList.length > 0) {
        let paramId = Math.ceil((Math.random() * 100));
        //组装数据
        let paramData = {
          'method': 'subs',
          'params': cirSubsList,
          'id': paramId
        };
        let subsCmd = {
          subsParam: paramData,
          onData: this.reFreshPlate
        };
        this.cirsubsId = 'svg' + paramId;
        subscribe(subsCmd);
      }
    },
    //刷新软压板
    reFreshPlate(dataList){
      for (var i = 0; i < dataList.length; i++) {
        let refreshData = dataList[i];
        let key = refreshData['key'];
        let value = refreshData['value'];
        if (this.plateCellMap[key] != undefined) {
          let currentValue = this.plateCellMap[key]['value'];
          if (value != currentValue) {
            this.plateCellMap[key]['value'] = value;
            let cell = this.plateCellMap[key]['cell']
            if(cell != undefined){
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
};
</script>

<style scoped>
.diagram-dialog-div {
  background-color: #021334;
  width: 100%;
  height: 100%
}
</style>
