<template>
  <div class="diagram-container">
    <div class="left-container">
      <div class="left-child" style="height: calc(30% - 10px);border-width: 0px;background-color: transparent">
        <el-tabs type="border-card">
          <el-tab-pane label="面板图" class="panel-light-tab">
            <ul class="panel-ul">
              <li v-for="item in plateList">
                <div class="panel-light" :id="item['F1016_CODE']+'-parent'" :dataType="item['F1011_NO']">
                  <div class="panel-light-inside" :id="item['F1016_CODE']" value-tag="2"
                       :dataType="item['F1011_NO']"></div>
                </div>
                <span class="panel-light-desc">{{ item['F1016_DESC']
                  }}</span>
              </li>
            </ul>
          </el-tab-pane>
          <el-tab-pane label="背板图" class="backboard-tab">
            <ul class="backboard-ul" v-for="desc in backboards" :id='desc' :style="{ width:`${backboardWidth}`} ">
              <li class="backboard-li" v-for="item in boardPoints[desc]">
                <div class="port-desc" style="text-align: right">{{ item['F1048_NO'] }}</div>
                <div class="backboard-port">
                  <div :id="item['F1016_CODE']" value-tag="2" class="backboard-port-inside"></div>
                </div>
                <div v-if="item['rx'] != undefined" style="margin-left: 5px;" class="rx-container">
                  <div class="backboard-port">
                    <div :id="item['rx']['F1016_CODE']" value-tag="2" class="backboard-port-inside"></div>
                  </div>
                  <div class="port-desc" style="text-align: left">
                    {{ item['rx'] ? item['rx']['F1048_NO'] : '' }}
                  </div>
                </div>
              </li>
            </ul>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div class="left-child" style="height: calc(20% - 10px)">
        <div class="left-item-title">硬压板</div>
        <div class="left-item-content">
          <div v-for="item in plpieceList" class="img-content">
            <img class="plate-img" :src="opensrc" :id="item['F1016_CODE']"  />
            <div>{{ item['F1016_DESC'] }}</div>
          </div>
        </div>
      </div>
      <div class="left-child">
        <div class="left-item-title">功能软压板</div>
        <div class="left-item-content">
          <div v-for="item in fsoftplatenList" class="img-content">
            <img class="plate-img" :src="opensrc" :id="item['F1016_CODE']"  />
            <div>{{ item['F1016_DESC'] }}</div>
          </div>
        </div>

      </div>
      <div class="left-child">
        <div class="left-item-title">出口软压板</div>
        <div class="left-item-content">
          <div v-for="item in esoftplatenList" class="img-content">
            <img class="plate-img" :src="opensrc" :id="item['F1016_CODE']"  />
            <div>{{ item['F1016_DESC'] }}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="right-container">
      <div class="right-child-top">
        <el-tabs type="border-card">
          <el-tab-pane label="告警信号">
            <div class="radio-div">
              <el-radio v-model="alarmRadio" label="0">显示全部</el-radio>
              <el-radio v-model="alarmRadio" label="1">仅显示告警</el-radio>
            </div>
            <div class="right-content">
              <div :class="'alarm-block filter-show-'+alarmRadio" v-for="item in alarmList"
                   :unalarm="item['F1018_NOALARM']" value-tag="2">
                <span class="alarm-circle" :id="item['F1016_CODE']" value-tag="2"
                      :unalarm="item['F1018_NOALARM']"></span>
                {{ item['F1016_DESC'] }}
              </div>
              <!--              <div :class="'alarm-block filter-show-'+alarmRadio"  unalarm="1"  value-tag="1" >-->
              <!--                <span class="alarm-circle" id="ertet" value-tag="1" unalarm="1"></span>-->
              <!--                jjjjjjj-->
              <!--              </div>-->
              <!--              <div :class="'alarm-block filter-show-'+alarmRadio"  unalarm="0"  value-tag="1" >-->
              <!--                <span class="alarm-circle" id="er1tet" value-tag="1" unalarm="0"></span>-->
              <!--                rrrrrr-->
              <!--              </div>-->
            </div>
          </el-tab-pane>
          <el-tab-pane label="保护动作">
            <div class="radio-div">
              <el-radio v-model="actionRadio" label="0">显示全部</el-radio>
              <el-radio v-model="actionRadio" label="1">仅显示动作</el-radio>
            </div>
            <div class="right-content">
              <div :class="'alarm-block filter-show-'+actionRadio" v-for="item in actionList"
                   :unalarm="item['F1018_NOALARM']" value-tag="2">
                <span class="alarm-circle" :id="item['F1016_CODE']" value-tag="2"
                      :unalarm="item['F1018_NOALARM']"></span>
                {{ item['F1016_DESC'] }}
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>

      </div>
      <div class="right-child-bottom">
        <el-tabs type="border-card">
          <el-tab-pane label="温度">
            <div class="radio-div">
              <el-radio v-model="tempRadio" label="0">显示全部</el-radio>
              <el-radio v-model="tempRadio" label="1">仅显示越限</el-radio>
            </div>
            <div class="right-content-bottom">
              <div :class="'value-show filter-overlimit-'+tempRadio" overlimit-tag="0"
                   :unalarm="item['F1017_NOALARM']" v-for="item in tempList">
                <div @contextmenu="showMenu($event)" class="value-span" :id="item['F1006_CODE']" overlimit-tag="0" :unalarm="item['F1017_NOALARM']">
                  0.00
                </div>
                <div class="desc-span">{{ item['F1006_DESC'] }}</div>
              </div>
              <!--              <div :class="'value-show-temp filter-overlimit-'+tempRadio" overlimit-tag="1" unalarm="1"  v-for="item in tempList">-->
              <!--                <div class="value-span" id="ttttt" overlimit-tag="1" unalarm="1">0.00</div>-->
              <!--                <div class="desc-span">dfgdfgdfg</div>-->
              <!--              </div>-->
              <!--              <div :class="'value-show-temp filter-overlimit-'+tempRadio" overlimit-tag="1" unalarm="0"  v-for="item in tempList">-->
              <!--                <div class="value-span" id="ttt2tt" overlimit-tag="1" unalarm="0">0.00</div>-->
              <!--                <div class="desc-span">dfgdf2gdfg</div>-->
              <!--              </div>-->
            </div>
          </el-tab-pane>
          <el-tab-pane label="电压">
            <div class="radio-div">
              <el-radio v-model="voltageRadio" label="0">显示全部</el-radio>
              <el-radio v-model="voltageRadio" label="1">仅显示越限</el-radio>
            </div>
            <div class="right-content-bottom">
              <div :class="'value-show filter-overlimit-'+voltageRadio " overlimit-tag="0"
                   :unalarm="item['F1017_NOALARM']" v-for="item in voltageList">
                <div @contextmenu="showMenu($event)" class="value-span" :id="item['F1006_CODE']" overlimit-tag="0" :unalarm="item['F1017_NOALARM']">
                  0.00
                </div>
                <div class="desc-span">{{ item['F1006_DESC'] }}</div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="光强">
            <div class="radio-div">
              <el-radio v-model="lightRadio" label="0">显示全部</el-radio>
              <el-radio v-model="lightRadio" label="1">仅显示越限</el-radio>
            </div>
            <div class="right-content-bottom">
              <div :class="'value-show filter-overlimit-'+lightRadio " overlimit-tag="0"
                   :unalarm="item['F1017_NOALARM']" v-for="item in lightList">
                <div @contextmenu="showMenu($event)" class="value-span" :id="item['F1006_CODE']" overlimit-tag="0" :unalarm="item['F1017_NOALARM']">
                  0.00
                </div>
                <div class="desc-span">{{ item['F1006_DESC'] }}</div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>

      </div>
    </div>
    <ul class="context-menu-ul" v-show="contextMenuShow" :style="{left:menuX+'px',top: menuY+'px'}">
      <li class="context-menu-li" @click="showHistory">历史事项</li>
      <li class="context-menu-li" @click="showDetail">详细信息</li>
    </ul>
    <event-list ref = 'eventList'></event-list>
    <analog-detail ref = 'analogDetail'></analog-detail>
  </div>
</template>

<script>
import * as statusJs from '@/api/gridnt/devicestatus';
import { subscribe, unsubscribe } from '@/api/gridnt/websocket';
import EventList from '@/views/gridnt/pomd/diagrams/statusinfo/eventList'
import AnalogDetail from '@/views/gridnt/pomd/diagrams/statusinfo/detail'
import _ from 'lodash';
import $ from 'jquery'

export default {
  name: 'StatusInfoDiagram',
  props: {
    nodeType: {
      type: String
    },
    activeTab: {
      type: String
    }
  },
  destroyed() {
    document.removeEventListener("mousedown",this.mouseHandler)
  },
  mounted() {
    let self = this
    self.mouseHandler = function(e){
      let target = e.target
      if(!$(target).hasClass("value-span") && !$(target).hasClass("context-menu-li")){
        self.contextMenuShow = false
      }
    }
    document.addEventListener("mousedown",self.mouseHandler)
  },
  watch: {
    nodeType: function(val) {
      if (val != 'I') {
        this.clearData();
      }
    },
    activeTab: function(val) {
      if (val != 'status') {
        this.clearData();
      }
    }
  },
  components:{ AnalogDetail, EventList},
  data() {
    return {
      mouseHandler: null,
      iedData: null,
      opensrc: require('@/assets/images/gridnt/plate/open.png'),
      closesrc: require('@/assets/images/gridnt/plate/close.png'),
      alarmRadio: '0',
      actionRadio: '0',
      tempRadio: '0',
      voltageRadio: '0',
      lightRadio: '0',
      deviceModel: '',
      plateList: [],
      plpieceList: [],
      fsoftplatenList: [],
      esoftplatenList: [],
      alarmList: [],
      actionList: [],
      tempList: [],
      voltageList: [],
      lightList: [],
      backboards: [],
      boardPoints: {},
      backboardWidth: '33%',
      subs1016Id: null,
      subsBackBoardId: null,
      subs1006Id: null,
      alarmColor: ['green', 'red', '#ccc'],
      plateRunColor: [ '#00ff00','#003000','#ccc'],//运行灯颜色
      platePowerColor: [ '#805200','#ffa500','#ccc'],//充电完成颜色
      plateOtherColor: ['#60281e','#ff2c2c','#ccc'],
      contextMenuShow: false,
      menuX:0,
      menuY:0,
      currentF1006Code: null,
      currentF1006Desc: '',
      eleMap:{} //存一下每个Key对应的node和现有值，防止大量遍历dom
    };
  },
  methods: {
    clearData() {
      this.plateList = [];
      this.plpieceList = [];
      this.fsoftplatenList = [];
      this.esoftplatenList = [];
      this.alarmList = [];
      this.actionList = [];
      this.tempList = [];
      this.voltageList = [];
      this.lightList = [];
      this.backboards = [];
      this.boardPoints = {};
      this.backboardWidth = '240px';
      if (this.subs1016Id != null) {
        unsubscribe(this.subs1016Id);
        this.subs1016Id = null;
      }
      if (this.subsBackBoardId != null) {
        unsubscribe(this.subsBackBoardId);
        this.subsBackBoardId = null;
      }
      if (this.subs1006Id != null) {
        unsubscribe(this.subs1006Id);
        this.subs1006Id = null;
      }
    },
    loadData(iedData) {
      let self = this;
      self.clearData();
      self.iedData = Object.assign({}, iedData);
      self.deviceModel = self.iedData.me.f1046Model;
      self.loadBackboard();
      self.loadPlate();
      self.loadAnalog();
    },
    /**
     *  背板灯
     */
    loadBackboard() {
      let self = this
      statusJs.loadBackboard(this.iedData.id).then((resp) => {
        this.backboards = resp.data['boards'];
        if (this.backboards.length == 1) {
          this.backboardWidth = '100%';
        } else if (this.backboards.length == 2) {
          this.backboardWidth = '49%';
        }
        this.boardPoints = resp.data['points'];
        self.$nextTick(() =>{
          // self.reFreshDiagram([{table:'status',key:'JSBState0000027357',value: 1}])
          // setTimeout(function(){
          //   self.reFreshDiagram([{table:'status',key:'JSBState0000027357',value: 0}])
          // },5000)
          // setTimeout(function(){
          //   self.reFreshDiagram([{table:'status',key:'JSBState0000027357',value: 1}])
          // },10000)
          self.subscribeBackBoardData();

        })

      });
    },
    /**
     *  F1016
     */
    loadPlate() {
      let self = this
      statusJs.loadPlate(this.iedData.id).then((resp) => {
        this.plateList = resp.data['PANELLIGHT']; //面板
        this.plpieceList = resp.data['PLPIECE']; //硬压板
        this.fsoftplatenList = resp.data['FSOFTPLATEN']; // 功能软压板
        this.esoftplatenList = resp.data['ESOFTPLATEN']; //出口软压板
        this.alarmList = resp.data['ALARM']; //告警
        this.actionList = resp.data['ACTION']; //动作
        self.$nextTick(() =>{
           self.subscribeF1016Data();
        })

      });
    },
    loadAnalog() {
      let self = this
      statusJs.loadAnalog(this.iedData.id).then((resp) => {
        this.tempList = resp.data['TEMP']; //温度
        this.voltageList = resp.data['VOLTAGE']; //电压
         this.lightList = resp.data['LIGHT']; //光强
        self.$nextTick(() =>{
          self.subscribeF1006Data();
        })


      });
    },
    /**
     *  订阅背板数据
     */
    subscribeBackBoardData() {
      if (this.subsBackBoardId != null) {
        unsubscribe(this.subsBackBoardId);
      }
      let subsData = [];
      for (let key in this.boardPoints) {
        let points = this.boardPoints[key];
        for (let i = 0; i < points.length; i++) {
          if (points[i]['F1016_CODE'] != undefined) {
            let f1016Code = points[i]['F1016_CODE']
            subsData.push({ table: 'status', 'key': f1016Code, cols: ['value'] });
            this.eleMap[f1016Code] = {value:2,cel:document.querySelector("#"+f1016Code)}
          }
          if (points[i]['rx'] != undefined) {
            if (points[i]['rx']['F1016_CODE'] != undefined) {
              let f1016Code = points[i]['rx']['F1016_CODE']
              subsData.push({ table: 'status', 'key': f1016Code, cols: ['value'] });
              this.eleMap[f1016Code] = {value:2,cel:document.querySelector("#"+f1016Code)}
            }
          }
        }
      }
      if (subsData.length > 0) {
        let paramId = Math.ceil((Math.random() * 100));
        //组装数据
        let paramData = {
          'method': 'subs',
          'params': subsData,
          'id': paramId
        };
        let subsCmd = {
          subsParam: paramData,
          onData: this.reFreshDiagram
        };
        this.subsBackBoardId = 'svg' + paramId;
        subscribe(subsCmd);
      }
    },
    subscribeF1016Data() {
      if (this.subs1016Id != null) {
        unsubscribe(this.subs1016Id);
      }
      let subsData = [];
      for (let i = 0; i < this.plateList.length; i++) {
        let f1016Code = this.plateList[i]['F1016_CODE']
        subsData.push({ table: 'status', 'key': f1016Code, cols: ['value'] });
        this.eleMap[f1016Code] = {value:2,cel:document.querySelector("#"+f1016Code)}
      }
      for (let i = 0; i < this.plpieceList.length; i++) {
        let f1016Code = this.plpieceList[i]['F1016_CODE']
        subsData.push({ table: 'status', 'key': f1016Code, cols: ['value'] });
        this.eleMap[f1016Code] = {value:2,cel:document.querySelector("#"+f1016Code)}
      }
      for (let i = 0; i < this.fsoftplatenList.length; i++) {
        let f1016Code = this.fsoftplatenList[i]['F1016_CODE']
        subsData.push({ table: 'status', 'key': f1016Code, cols: ['value'] });
        this.eleMap[f1016Code] = {value:2,cel:document.querySelector("#"+f1016Code)}
      }
      for (let i = 0; i < this.esoftplatenList.length; i++) {
        let f1016Code = this.esoftplatenList[i]['F1016_CODE']
        subsData.push({ table: 'status', 'key': f1016Code, cols: ['value'] });
        this.eleMap[f1016Code] = {value:2,cel:document.querySelector("#"+f1016Code)}
      }
      for (let i = 0; i < this.alarmList.length; i++) {
        let f1016Code = this.alarmList[i]['F1016_CODE']
        subsData.push({ table: 'status', 'key': f1016Code, cols: ['value'] });
        this.eleMap[f1016Code] = {value:2,cel:document.querySelector("#"+f1016Code)}
      }
      for (let i = 0; i < this.actionList.length; i++) {
        let f1016Code = this.actionList[i]['F1016_CODE']
        subsData.push({ table: 'status', 'key': f1016Code, cols: ['value'] });
        this.eleMap[f1016Code] = {value:2,cel:document.querySelector("#"+f1016Code)}
      }
      if (subsData.length > 0) {
        let paramId = Math.ceil((Math.random() * 100));
        //组装数据
        let paramData = {
          'method': 'subs',
          'params': subsData,
          'id': paramId
        };
        let subsCmd = {
          subsParam: paramData,
          onData: this.reFreshDiagram
        };
        this.subs1016Id = 'svg' + paramId;
        subscribe(subsCmd);
      }
    },
    /**
     * 订阅遥测数据，越限告警
     */
    subscribeF1006Data() {
      if (this.subs1006Id != null) {
        unsubscribe(this.subs1006Id);
      }
      let subsData = [];
      for (let i = 0; i < this.tempList.length; i++) {
        let f1006Code = this.tempList[i]['F1006_CODE']
        subsData.push({ table: 'analog', 'key': f1006Code, cols: ['value', 'overlimitflag'] });
        this.eleMap[f1006Code] = {value:0.00,overlimit:0,cel:document.querySelector("#"+f1006Code)}
      }
      for (let i = 0; i < this.voltageList.length; i++) {
        let f1006Code = this.voltageList[i]['F1006_CODE']
        subsData.push({ table: 'analog', 'key': f1006Code, cols: ['value', 'overlimitflag'] });
        this.eleMap[f1006Code] = {value:0.00,overlimit:0,cel:document.querySelector("#"+f1006Code)}
      }
      for (let i = 0; i < this.lightList.length; i++) {
        let f1006Code = this.lightList[i]['F1006_CODE']
        subsData.push({ table: 'analog', 'key': f1006Code, cols: ['value', 'overlimitflag'] });
        this.eleMap[f1006Code] = {value:0.00,overlimit:0,cel:document.querySelector("#"+f1006Code)}
      }
      if (subsData.length > 0) {
        let paramId = Math.ceil((Math.random() * 100));
        //组装数据
        let paramData = {
          'method': 'subs',
          'params': subsData,
          'id': paramId
        };
        let subsCmd = {
          subsParam: paramData,
          onData: this.reFreshDiagram
        };
        this.subs1006Id = 'svg' + paramId;
        subscribe(subsCmd);
      }
    },
    reFreshDiagram(valueList) {
      //刷新背板背景
      //有背板数据                有背板告警
      let hasBackBoard = false,hasBackBoardAlarm = false
      for (var i = 0; i < valueList.length; i++) {
        let valueItem = valueList[i];
        let newVal = valueItem['value'];
        if (this.eleMap[valueItem.key] !=undefined) {
          let domEle = this.eleMap[valueItem.key]['cel']
          let classList = domEle.classList;
          let oldVal = this.eleMap[valueItem.key]['value']
          if (valueItem.table == 'status') {
            let tagName = domEle.tagName.toLowerCase();
            if (oldVal != newVal) {
              this.eleMap[valueItem.key]['value'] = newVal
              //属性关联样式展示，要与实际值相同
              domEle.setAttribute('value-tag', newVal);
              //压板
              if (tagName == 'img') {
                domEle.setAttribute('src', newVal == 0 ? this.opensrc : this.closesrc);
              } else if (_.includes(classList, 'backboard-port-inside')) {
                //背板
                hasBackBoard = true
                let newColor = this.alarmColor[newVal];
                domEle.style.backgroundColor = newColor;
                domEle.parentNode.style.border = '2px solid ' + newColor;
                //有告警，背板得闪
                if(newVal == 1){
                  hasBackBoardAlarm = true
                  let eleul = $(domEle).parents('.backboard-ul').eq(0)
                  if(!eleul.hasClass("alarm-back")){
                    eleul.addClass('alarm-back')
                  }
                }
              } else if (_.includes(classList, 'alarm-circle')) {
                //告警，动作
                domEle.parentElement.setAttribute('value-tag', newVal);
              } else if (_.includes(classList, 'panel-light-inside')) {
                //面板
                let newColor = this.plateOtherColor[newVal];
                let f1011No = domEle.getAttribute('dataType');
                //运行状态是 0是绿色。充电橙色，其他是红色
                if (f1011No == 204) {
                  newColor = this.plateRunColor[newVal];
                }else if(f1011No == 210){
                  newColor = this.platePowerColor[newVal];
                }
                domEle.style.backgroundColor = newColor;
                domEle.parentNode.style.border = '2px solid ' + newColor;
              }
            }
          } else if (valueItem.table == 'analog') {
            let overlimit = this.eleMap[valueItem.key]['overlimit']
            //温度，电压、光强
            if (_.includes(classList, 'value-span')) {
              if (newVal !== oldVal) {
                this.eleMap[valueItem.key]['value'] = newVal
                domEle.innerHTML = newVal;
              }
              let overLimitNew = valueItem['overlimitflag'];
              //有越限信息
              if (overLimitNew != undefined) {
                if (overLimitNew !== overlimit) {
                  this.eleMap[valueItem.key]['overlimit'] = overLimitNew
                  domEle.setAttribute('overlimit-tag', overLimitNew);
                  domEle.parentElement.setAttribute('overlimit-tag', overLimitNew);
                }
              }
            }
          }
        }
      }
      //有背板数据，没有背板告警，查询一下是否需要去除闪烁
      if(hasBackBoard){
        if(!hasBackBoardAlarm){
          let uls = document.querySelectorAll(".backboard-ul")
          for(let i = 0;i<uls.length;i++){
            let ulele = uls[i]
            if($(ulele).hasClass("alarm-back")){
              //查询是否有越限，没有越限去除闪烁
              let childs =  $(ulele).has(".backboard-port-inside[value-tag='1']")
              if(childs.length == 0){
                $(ulele).removeClass('alarm-back')
              }
            }

          }
        }
      }
    },
    /**
     * 温度、电压、光强右键菜单
     * @param e
     */
    showMenu(e){
      e.preventDefault()
      let ele = e.target
      // console.log(ele)
      this.currentF1006Code = ele.getAttribute("id")
      this.currentF1006Desc = $('#'+this.currentF1006Code).next().html()
      this.menuX = e.clientX - 350;
      this.menuY = e.clientY - 98 -40-15
      if(!this.contextMenuShow){
        this.contextMenuShow = true
      }
    },
    /**
     * 历史数据
     */
    showHistory(){
      this.contextMenuShow = false
      this.$refs.eventList.showDialog(this.currentF1006Code,this.currentF1006Desc)
    },
    /**
     * 详情
     */
    showDetail(){
      this.contextMenuShow = false
      this.$refs.analogDetail.showDialog(this.currentF1006Code)
    }
  }
};
</script>
<style scoped src="../../../../../assets/styles/gridnt/diagram.css"></style>
<style scoped src="../../../../../assets/styles/gridnt/statusinfo.css"></style>

<style >


</style>
