<template>
  <div class="diagram-container" v-loading = "loading">
    <div id="physics-paper" class="page-div"></div>
    <physicsrel ref  = "physicsrelRef"></physicsrel>
  </div>
</template>

<script>

import * as joint from 'jointjs';
import * as physicsJs from '@/api/gridnt/physconn'
import * as physicsDrawJs from '@/api/gridnt/draw/physics'
import SvgPanZoom from 'svg-pan-zoom';
import * as commonDrawJs from '@/api/gridnt/draw/common';
import Physicsrel from '@/views/gridnt/pomd/diagrams/physics/physicsrel'
import { subscribe, unsubscribe } from '@/api/gridnt/websocket';
import $ from 'jquery'


export default {
  name: 'PhysicsDiagram',
  mounted() {
    this.initDatas();
  },
  components:{Physicsrel},
  data(){
    return{
      loading: false,
      mainIedInfo: null,
      mainIedCell: null,
      paper: null,
      graph: null,
      physicsData: null,
      pagePadding:60,
      pageWidth:0,
      sideIedWidth:300,
      sideIedTopMargin:50,
      mainIedWidth:200,
      iedInitHeight: 200,
      iedInitY:60,
      textWidth:150,
      svgPan: null,
      physicsLineMargin: 50,
      subsId :null,
      physicsLineColor: ['green', 'red',"#fff"],
      physicsStatusDesc: ['正常', '中断', '数据无效'],
      subsData :[],
      cellMap : {}
    }
  },
  computed:{
    mainIedX:function(){
      return (this.pageWidth  - this.mainIedWidth)/2
    }
  },
  props:{
    nodeType:{
      type: String
    },
    activeTab: {
      type: String
    },
  },
  watch:{
    nodeType:function(val){
      if(val != 'I'){
        this.clear()
      }
    },
    activeTab: function(val) {
      if (val != 'physics') {
        this.clear();
      }
    }
  },
  methods:{
    clear(){
      if(this.graph != null){
        this.graph.clear();
      }
      if(this.svgPan != null){
        this.svgPan.destroy()
        this.svgPan = null
      }
      this.mainIedWidth = 300
      if(this.subsId != null){
        unsubscribe(this.subsId)
        this.subsId = null
      }
      this.subsData = []
      this.cellMap = {}
      this.mainIedCell = null;
    },
    initDatas() {
      let self = this;
      self.graph = new joint.dia.Graph;
      self.paper = new joint.dia.Paper({
        el: document.getElementById('physics-paper'),
        width: '100%',
        height: '100%',
        // gridSize: 20,
        model: self.graph,
        markAvailable: false,
        interactive: false
      });
      this.handleListener();
    },
    handleListener() {
      let self = this
      //监听line点击，弹出dialog
      self.paper.on('link:pointerdown', function(elementView, e) {
        e.stopPropagation()
        let ele = elementView.model
        let viewType = ele.get('viewType');
        let physicsData = ele.get('physicsData');
        if ('portConn' == viewType) {
          if(physicsData['f1016Statuscode'] != undefined){
            let value = self.cellMap[physicsData['f1016Statuscode']]['value']
            let desc = "状态："+self.physicsStatusDesc[value]
            self.$nextTick(() =>{
              self.$refs.physicsrelRef.showDialog(self.subInfo,physicsData,desc)
            })
          }

        }
      });
    },
    loadData(iedInfo){
      this.clear()
      this.loading = true
      this.pageWidth = document.getElementById('physics-paper').clientWidth
      this.mainIedInfo = Object.assign({}, iedInfo);
      // this.drawPortsTests()
      let param= {
        f1046Code: this.mainIedInfo.id
      }
      let self = this
      //按IED查询物理回路
      physicsJs.loadPhysGroup(param).then(resp =>{
        //画当前Ied
        self.drawMainIed();
        self.physicsData = resp.data
        if(self.physicsData != null){
          if(self.physicsData[1].length > 0){
            self.mainIedWidth = self.mainIedWidth* 1.5
            self.mainIedCell.position(self.mainIedX,self.mainIedCell.position().y)
            self.mainIedCell.size(self.mainIedWidth,self.mainIedCell.size().height)
          }else{
            //右侧没有数据的话要偏移
            self.mainIedCell.prop("attrs/text/xAlignment","left")
          }
          // 画对端Ied
          self.drawSideIeds(0,self.physicsData[0])
          self.drawSideIeds(1,self.physicsData[1])
          commonDrawJs.ResizeMainIed(this.physicsData,this.iedInitY,this.graph,this.mainIedCell);
          self.subscribeData()
          // 绑定 拖动 放缩插件
          this.svgPan = SvgPanZoom('#physics-paper svg', {
            zoomEnabled: true,
            controlIconsEnabled: false,
            fit: false,
            center: false,
            dblClickZoomEnabled: false,
          });
          //在svg里面添加  闪烁的 class
          var SVGDocument = $("#physics-paper svg")[0]
          var style = document.createElement("style");
          style.setAttribute("type", "text/css");
          var styleContent = document.createTextNode(
            "@keyframes red_spin {from {stroke: #fff;} to {stroke: #ff0000;}}" +
            ".alarm-back {animation: red_spin 1s steps(2, start) infinite;}"
          );
          style.appendChild(styleContent);
          SVGDocument.children[0].appendChild(style);
        }

      }).finally(() =>{
        this.loading = false
      })
    },
    drawMainIed(){
      let position = {x:this.mainIedX,y:this.iedInitY}
      let size={width:this.mainIedWidth,height:this.iedInitHeight}
      let iedDesc = this.mainIedInfo.label
      let iedName = iedDesc.split(":")[0]+"\n"
      let showDesc = joint.util.breakText(iedDesc.split(":")[1],{width:this.textWidth})
      this.mainIedCell= physicsDrawJs.drawIedCell(this.mainIedInfo.id,position,size,iedName+showDesc)
      this.graph.addCell(this.mainIedCell)
    },
    /**
     * 画两边的Ied
     * @param sideIndex
     * @param iedGroups
     */
    drawSideIeds(sideIndex,iedGroups){
      let iedX = this.pagePadding
      if(sideIndex == 1){
        iedX = this.pageWidth - this.pagePadding - this.sideIedWidth
      };
      let iedCells = []
      if(iedGroups.length > 0){
        for(let i = 0;i<iedGroups.length;i++){
          let iedItems = iedGroups[i]
          let iedInfo = iedItems.iedInfo
          let positionY = 0
          if(iedCells.length == 0){
            positionY = this.iedInitY
          }else{
            positionY = iedCells[iedCells.length -1].position().y + iedCells[iedCells.length -1].size().height +  this.sideIedTopMargin
          }
          let position = {x:iedX,y:positionY}
          let height = this.iedInitHeight
          let iedName = iedInfo['f1046Name']+"\n";
          let showDesc = joint.util.breakText(iedInfo['f1046Desc'],{width:this.textWidth})
          // 计算一下当前Ied的高度
          let physicsList = (iedItems.recv ? iedItems.recv.length: 0) + (iedItems.send ? iedItems.send.length:0)
          if(physicsList > 3){
            height = height + (physicsList - 3) * this.physicsLineMargin
          }
          let textPosition = sideIndex == 0 ? "right":"left"
          let size={width:this.sideIedWidth,height:height}
          let sideIedCell =physicsDrawJs.drawIedCell(iedInfo['f1046Code'],position,size,iedName+showDesc)
          sideIedCell.prop("attrs/text/xAlignment",textPosition)
          sideIedCell.addTo(this.graph)
          iedCells.push(sideIedCell)
          //画物理连接
          this.drawPhysics(sideIndex,sideIedCell,iedItems)
        }
        iedCells = []
      }
      // else{
      //   let sideIedCell =physicsDrawJs.drawIedCell('blank', { x:iedX,y: this.iedInitY}, { width:this.sideIedWidth,height: 1 },"")
      //   sideIedCell.attr("body/stroke",'transparent')
      //   sideIedCell.attr("body/fill",'transparent')
      //   this.graph.addCell(sideIedCell)
      // }
    },
    /**
     * 画物理链接点
     * @param iedCells
     * @param sideIndex
     * @param iedCell
     * @param iedItems
     */
    drawPhysics(sideIndex,iedCell,iedItems){
      let startMarginTop = 0
      let physicsLen = (iedItems.recv ? iedItems.recv.length: 0) + (iedItems.send ?iedItems.send.length: 0)
      if(physicsLen > 3){
        startMarginTop = (this.iedInitHeight - 4*physicsDrawJs.portWidth- 3* this.physicsLineMargin) /2
      }else{
        startMarginTop = (this.iedInitHeight - physicsDrawJs.portWidth*physicsLen - (physicsLen -1)* this.physicsLineMargin) /2
      }
      if(iedItems.recv != undefined){
        this.drawLins(sideIndex,iedCell,startMarginTop,iedItems,"recv")
      }
      if(iedItems.send != undefined){
        this.drawLins(sideIndex,iedCell,startMarginTop,iedItems,"send")
      }

    },
    drawLins(sideIndex,iedCell,startMarginTop,iedItem,type){
      let physicsList = iedItem[type]
      let portSize = iedCell.getPorts().length
      //确定port的group
      let sideGroupName = 'group'+(sideIndex == 0 ?'Right':'Left')
      let mainGroupName = 'group'+(sideIndex == 0 ?'Left':'Right')
      let sidePortDescName  = type =='send' ? 'f1048NoA':'f1048NoB'
      let mainPortDescName  = type =='send' ? 'f1048NoB':'f1048NoA'
      let mainPortType = type =='send' ? 'recv':'send'
      for(let i = 0;i<physicsList.length;i++){
        let physicsItem = physicsList[i]
        let sideTop = startMarginTop+this.physicsLineMargin*(i+portSize)
        let mainTop = iedCell.position().y + sideTop - this.iedInitY
        iedCell.addPort(
          { id:physicsItem['f1053Code']+"-"+type,group: sideGroupName,args: { y: sideTop } , attrs: {
            label: { text: physicsItem[sidePortDescName]},
            // body:{class:'alarm-back'}
            }}
        );
        this.mainIedCell.addPort(
          {id:physicsItem['f1053Code']+"-"+mainPortType, group: mainGroupName,args: { y: mainTop } , attrs: {
            label: { text: physicsItem[mainPortDescName] }
          }}
        );
        let sendIed = type =='send' ? iedCell:this.mainIedCell
        let recvIed = type =='send' ? this.mainIedCell:iedCell
        //连线
        var l1 = new joint.dia.Link({
          id:physicsItem['f1053Code'],
          source: { id: sendIed.id, port: physicsItem['f1053Code']+'-send' },
          target: { id: recvIed.id, port: physicsItem['f1053Code']+'-recv' },
          viewType: 'portConn',
          physicsData:physicsItem,
          attrs: {
            cursor: 'pointer',
            line: {
              stroke: '#fff',
              strokeWidth: 1,
            },
            '.marker-target': {
              d: 'M 10 0 L 0 5 L 10 10 z',
              fill: '#fff'
            },
            '.connection': {
              stroke: '#fff',
              fill: 'none',
            },
            '.link-tools': {
              display: 'none'
            }
          }
        });
        this.graph.addCells(iedCell,l1)
        //组织订阅数据
        if(physicsItem['f1016Statuscode'] != undefined){
          this.subsData.push({table:"status",key:physicsItem['f1016Statuscode'],cols:['value']})
          this.cellMap[physicsItem['f1016Statuscode']] = {value:'2',cells:[l1]}
        }
      }
    },
    /**
     * 订阅
     * @param subscirbeData
     * @param callback
     * @param f1065Code
     */
    subscribeData() {
      if (this.subsId != null && this.subsId != '') {
        //先取消订阅
        unsubscribe(this.subsId);
      }
      if (this.subsData.length > 0) {
        //重新处理一下数据,添加port
        for(var key in this.cellMap){
          let cells = this.cellMap[key]['cells']
          // let lineId = cells.getId()
          let link = cells[0]
          let sendPort = link.source().port
          let targetPort = link.target().port
          // port 直接放dom 元素
          cells.push($("rect[port='"+sendPort+"']")[0],$("rect[port='"+targetPort+"']")[0])
        }
        let paramId = Math.ceil((Math.random() * 100));
        //组装数据
        let paramData = {
          'method': 'subs',
          'params': this.subsData,
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
      // console.log("main:"+dataList.length)
      for (var i = 0; i < dataList.length; i++) {
        let refreshData = dataList[i];
        let key = refreshData['key'];
        let value = refreshData['value'];
        if (this.cellMap[key] != undefined) {
          let currentValue = this.cellMap[key]['value'];
          if (value != currentValue) {
            let color = this.physicsLineColor[value]
            this.cellMap[key]['value'] = value;
            let cells = this.cellMap[key]['cells'];
            for(var k = 0;k<cells.length;k++){
              let cell = cells[k]
              //断开需要变红，闪烁
              if(value == 1){
                //线
                if(cell instanceof joint.dia.Link){
                  cell.attr('.connection/stroke','')
                  cell.attr('.marker-target/fill','') //箭头
                  cell.attr('.connection/class','alarm-back')
                  cell.attr('.marker-target/class','alarm-back')//箭头
                }else{
                  // 端点
                  cell.setAttribute("class",'alarm-back')
                }
              }else{
                if(cell instanceof joint.dia.Link){
                  cell.attr('.connection/stroke',color) //线
                  cell.attr('.marker-target/fill',color) //箭头
                  cell.attr('.connection/class','connection') //线
                  cell.attr('.marker-target/class','marker-target')//箭头
                }else{
                  // 端点
                  cell.setAttribute("class",'')
                  cell.setAttribute("stroke",color)
                }
              }
            }
            //如果打开了dialog,直接刷新对应的数据状态
            let spans = $("."+key+"-phy-status > tspan")
            if(spans.length > 0){
              let newText = "状态："+this.physicsStatusDesc[value]
              spans[0].innerHTML = newText
            }
          }
        }
      }

    }
  }
};
</script>

<style scoped src="../../../../../assets/styles/gridnt/diagram.css"></style>
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
  cursor: pointer;
}

/deep/ .link-tools {
  opacity: 0;
  /*cursor: pointer;*/
}

/deep/ .el-loading-mask{
  background-color: rgba(255,255,255,0.5);
}

@keyframes red-spin {
  from{
    stroke: transparent;
  }
  to{
    stroke: #ff0000;
  }
}

.alarm-back{
  animation: red-spin 1s steps(2,start) infinite;
}
</style>
