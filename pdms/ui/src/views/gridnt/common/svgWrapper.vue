<template>
  <div style="display: flex; flex-direction: column; flex: 1; height: calc(100% - 55px); position: relative">
    <div id="svgDivElement" ref="svgDivElementRef"></div>
  </div>
</template>

<script>
import _ from 'lodash';
import  $ from 'jquery';
import SvgPanZoom from 'svg-pan-zoom';
import {subscribe,unsubscribe} from '@/api/gridnt/websocket';

export default {
  name: 'SvgWrapper',
  data() {
    return {
      $svgdom: null,
      useDomCache: {},
      svgUseMap:{},
      svgTextMap: {},
      svgLineMap: {},
      svgName: '',
      flag: {
        dialogVisible: false
      },
      item: null,
      subsId : null,
      lineColor:['green','red',"rgb(255,255,255)"]
    };
  },
  props: {
    initSvgName: {
      type: String
    },
    svgBackground: {
      type: String,
      default() {
        return '#ffff';
      }
    },
    nodeType: {
      type: String
    }
  },
  watch: {
    initSvgName: {
      immediate: false,
      handler() {
        this.renderSvg();
      }
    },
    nodeType: function(val) {
      if (val != 'S') {
        this.clearData();
      }
    }
  },
  mounted() {
    // this.clearData();
    // this.renderSvg();
    console.log("111111111")
  },
  beforeDestroy() {
    this.clearData();
    console.log("22222")
  },
  methods: {
    renderSvg() {
      this.svgName = this.initSvgName;
      let style = 'width: 100%;height: 100%; flex: 1;display: flex; flex-direction: column';
      this.$refs.svgDivElementRef.style = style + ';background: ' + this.svgBackground;
      this.initSvg();
    },
    handleSelect(index) {
      let fileName = index;
      this.redirectSvg(null, 3, fileName);
      // console.info(arguments);
    },
    redirectSvg(event, index, svgName) {
      // console.info('redirectSvg :' + svgName);
      window.parent.postMessage({ iotSvg: 'web', name: svgName }, '*');
      if (event) {
        event.preventDefault();
      }
      // this.breadcrumblist.splice(index, this.breadcrumblist.length - index)
      this.svgName = svgName;
      this.initSvg();
    },
    initSvg() {
      this.clearData();
      let self = this;
      if (!this.svgName) {
        return;
      }
      // console.log(this.svgName)
      $('#svgDivElement').load(this.svgName, function(response, repomsestatus) {
        if (repomsestatus === 'success') {
          self.freshSvg();
          self.addEvent();
          self.addTeleRefresh();
        }
      });
    },
    addEvent() {
      let self = this;
      $('text').css('cursor', 'pointer');
      //如果有gridntyk，即可以进行遥控
    },

    //整理订阅数据
    addTeleRefresh(){
      let pathRegExp = /(\S+\/){2}\S+/g
      let self = this
      let subscribeData = {}
      // let
      this.$svgdom.find('dyndata[type="0"]').each(function(index,ele){
        let path = ele.getAttribute("path");
        path = path.substring(4,path.length)
        pathRegExp.lastIndex = 0
        if(pathRegExp.test(path)){
          let key = path.substring(0,path.indexOf("/"));
          let tableName = path.substring(path.indexOf("/")+1,path.lastIndexOf("/"));
          let column = path.substring(path.lastIndexOf("/")+1);
          if(!_.has(subscribeData,tableName+"/"+key)){
            subscribeData[tableName+"/"+key] = []
          }
          let columnArr = subscribeData[tableName+"/"+key]
          if(!_.find(columnArr,column)){
            subscribeData[tableName+"/"+key].push(column)
          }
          let parent = ele.parentNode
          let parentTagName = parent.tagName.toLowerCase()
          if(parentTagName == 'use'){
            if(self.svgUseMap[path] == undefined){
              self.svgUseMap[path] = []
            }
            self.svgUseMap[path].push(parent)
          }else if(parentTagName == 'line'){
            if(self.svgLineMap[path] == undefined){
              self.svgLineMap[path] = []
            }
            self.svgLineMap[path].push(parent)
            let pathMarker = parent.style['marker-end'] || parent.style['marker-start']
            if(pathMarker != '') {
              let markerUrl = pathMarker.substring(pathMarker.indexOf('"') + 2, pathMarker.lastIndexOf('"'));
              // console.log(markerUrl)
              let markPath = $("#" + markerUrl + ' path')[0]
              self.svgLineMap[path].push(markPath)
            }
          }else if(parentTagName == 'text'){
            // 获取textNode
            let children = parent.childNodes
            for (var i = 0; i < children.length; i++) {
              let child = children[i]
              if(child.nodeType == 3 && child.nodeValue.replaceAll(' ','') !='\n'){
                if(self.svgTextMap[path] == undefined){
                  self.svgTextMap[path] = new Array()
                }
                self.svgTextMap[path].push(child)
                break
              }
            }
          }
        }
      })
      if(JSON.stringify(subscribeData)  !='{}'){
        this.subscribeSignals(subscribeData)
      }

    },
    subscribeSignals(subscribeData){
      if(this.subsId != null && this.subsId != ''){
        //先取消订阅
        unsubscribe(this.subsId)
      }
      let patamList = []
      for(let tableKeys in subscribeData){
        let tableKeyArray = tableKeys.split("/");
        patamList.push({"table":tableKeyArray[0],"key": tableKeyArray[1],"cols":subscribeData[tableKeys]})
      }
      let paramId = Math.ceil((Math.random()*100))
      //组装数据
      let paramData = {
        "method": "subs",
        "params":patamList,
        "id": paramId
      }
      let subsCmd = {
        subsParam :paramData,
        onData:this.refreshHeze
      }
      this.subsId = "svg"+paramId
      subscribe(subsCmd)
    },
    freshSvg() {
      //修改属性，是svg充满全屏
      this.$svgdom = $('#svgDivElement svg');
      if (!this.$svgdom[0]) {
        return;
      }
      // this.$svgdom[0].removeAttribute('preserveAspectRatio');
      // this.$svgdom[0].removeAttribute('viewBox');
      this.$svgdom[0].setAttribute('height', '97%');
      this.$svgdom[0].setAttribute('width', '100%');
      //防止 g 标签"点穿"
      let svgStyle = this.$svgdom[0].getAttribute('style');
      if (!svgStyle) {
        svgStyle = 'flex: 1;pointer-events: fill;';
      } else {
        svgStyle = 'flex: 1;pointer-events: fill;' + svgStyle;
      }
      this.$svgdom[0].setAttribute('style', svgStyle);
      let filepack = this.svgName.substring(0,this.svgName.lastIndexOf("/"));
      //隐藏标题和点击行
      $('#svgDivElement svg image').each(function(index,item){
        if(item.getAttribute("xlink:href") != undefined){
          let xlinkHref = item.getAttribute("xlink:href");
          xlinkHref = filepack+"/"+xlinkHref;
          item.setAttribute("xlink:href",xlinkHref)
        }
      })
      // $('#svgDivElement svg text dyndata[path="标题///////"]').parent('text').css("display","none")
      //平移限制
      let beforePan = function(oldPan, newPan) {
        let gutterWidth = 100,
          gutterHeight = 100,
          // Computed variables
          sizes = this.getSizes(),
          leftLimit = -((sizes.viewBox.x + sizes.viewBox.width) * sizes.realZoom) + gutterWidth,
          rightLimit = sizes.width - gutterWidth - sizes.viewBox.x * sizes.realZoom,
          topLimit = -((sizes.viewBox.y + sizes.viewBox.height) * sizes.realZoom) + gutterHeight,
          bottomLimit = sizes.height - gutterHeight - sizes.viewBox.y * sizes.realZoom;
        let customPan = {};
        customPan.x = Math.max(leftLimit, Math.min(rightLimit, newPan.x));
        customPan.y = Math.max(topLimit, Math.min(bottomLimit, newPan.y));
        return customPan;
      };
      //绑定 拖动 放缩插件
      SvgPanZoom(this.$svgdom[0], {
        zoomEnabled: true,
        controlIconsEnabled: false,
        fit: 1,
        center: 1,
        customEventsHandler: null,
        dblClickZoomEnabled: false,
        beforePan: beforePan
      });
    },

    refreshHeze(valList){
      let self = this
      self.$nextTick(() =>  {
        _.forEach(valList,function(item){
          let prekey = item.key+"/"+item.table
          for(let colName in item){
            if(colName !='key' && colName !='table'){
              let fullKey = prekey+"/"+colName
              let value = item[colName]
              if(_.has(self.svgUseMap,fullKey)){
                self.updateSvgUse(fullKey,value)
              }else if(_.has(self.svgTextMap,fullKey)){
                self.updateSvgText(fullKey,value)
              }else if(_.has(self.svgLineMap,fullKey)){
                self.updateSvgLine(fullKey,value)
              }
            }
          }
        })
      })
    },

    updateSvgUse(key,value){
      let eles = this.svgUseMap[key]
      for(let i = 0;i<eles.length;i++){
        let ele = eles[i];
        //获取
        let href = ele.getAttribute("xlink:href");
        href = href.substring(1);
        let symbol = this.$svgdom.find("defs>symbol[id='"+href+"']")[0];
        let symbolValue = symbol.getAttribute("status")
        if(value != symbolValue){
          let customerId = symbol.getAttribute("custom_id");
          //获取当前customer相同，status与实际值相同的对象
          let relSymbols = this.$svgdom.find("defs>symbol[status='"+value+"'][custom_id='"+customerId+"']")
          if(relSymbols.length > 0){
            let newHref = relSymbols[0].getAttribute("id")
            ele.setAttributeNS('http://www.w3.org/1999/xlink','xlink:href','#'+newHref);
          }
        }
      }
    },
    updateSvgLine(key,value){
      let eles = this.svgLineMap[key]
      let color = this.lineColor[value]
      if(eles.length > 0){
        let line = eles[0]
        let stroke = line.style.stroke
        if(stroke != color ){
          for(let i = 0;i<eles.length;i++) {
            let ele = eles[i]
            if (ele.tagName.toLowerCase() == 'line') {
              ele.style.stroke = color
            } else if (ele.tagName.toLowerCase() == 'path') {
              //箭头
              ele.setAttribute("stroke", color)
              ele.setAttribute("fill", color)
            }
          }
        }
      }
    },

    updateSvgText(key,value){
      let eles = this.svgTextMap[key]
      for(let i = 0;i<eles.length;i++){
        let ele = eles[i];
        //获取
        let oldText = ele.nodeValue;
        if(value != oldText){
          ele.nodeValue = value
        }
      }
    },


    clearData: function() {
      console.log("unsubs............")
      $('#svgDivElement').html('');
      this.svgTextMap = {}
      this.svgLineMap = {}
      this.svgUseMap = {}
      if(this.subsId != null){
        unsubscribe(this.subsId)
        this.subsId = null
      }

    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 0;
  margin: 0;
}

.svg-main-style {
  width: 100%;
  height: 100%;
  background-color: #000;
}

use[elementtype='transformer'][gridntznhref]:hover {
  cursor: pointer;
}

.el-breadcrumb__inner a,
.el-breadcrumb__inner.is-link {
  font-weight: 700;
  text-decoration: none;
  -webkit-transition: color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  color: #0e95d2;
}

.el-breadcrumb__inner a:hover,
.el-breadcrumb__inner.is-link:hover {
  color: #6fbce2;
  cursor: pointer;
  font-weight: bold !important;
}
</style>
