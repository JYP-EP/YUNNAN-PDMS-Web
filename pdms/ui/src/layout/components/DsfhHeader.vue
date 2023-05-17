<template>
<!--  <div class="head-title">-->
<!--    <div style="display: flex; flex-direction: row; width: 100%; z-index: 999">-->
<!--      cccc-->
<!--    </div>-->

<!--  </div>-->
  <div class="tabs">
    <div class="tabs-logo" @click="goToIndex">
      <img  :src="logo" alt="">
    </div>
    <div class="button">
      <div>
        <img class="svg-img" :src="centralizedMonitoring" alt="">
        <div>集中监视</div>
      </div>
    </div>
    <div class="button">
      <div>
        <img class="svg-img" :src="farAwayPatrol" alt="">
        <div>远方巡视</div>
      </div>
    </div>
    <div class="button">
      <div>
        <img class="svg-img" :src="dataMaintenance" alt="">
        <div>智能维护</div>
      </div>
    </div>
    <div class="button">
      <div>
        <img class="svg-img" :src="intelligentScheduling" alt="">
        <div>智慧调度</div>
      </div>
    </div>
    <div class="button">
      <div>
        <img class="svg-img" :src="panoramicData" alt="">
        <div>全景数据</div>
      </div>
    </div>
    <div class="button">
      <div>
        <img class="svg-img" :src="databaseMinus" alt="">
        <div>数据维护</div>
      </div>
    </div>
    <div @click="goToAlert">
      <div class="tabs-user tabs-alarm">
        <img class="svg-img" :src="alarm" alt="">
        <span class="tabs-alarm-span">99+</span>
      </div>
    </div>
<!--    <div>-->
<!--      <div class="tabs-user">-->
<!--        <img class="svg-img" :src="user" alt="">-->
<!--        <div>admin</div>-->
<!--      </div>-->
<!--    </div>-->
    <el-row class="block-col-2">
      <el-col :span="12">
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
            <div class="tabs-user">
              <img class="svg-img" :src="user" alt="">
              <div>admin</div>
            </div>
          </span>
          <el-dropdown-menu>
            <el-dropdown-item icon="el-icon-lock" command="updatePwd">修改密码</el-dropdown-item>
            <el-dropdown-item icon="el-icon-switch-button" command="exit">退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-row>
  </div>

</template>

<script>
//logo图标无法选中拖动
['dragstart'].forEach(function(ev){
  document.addEventListener(ev, function(ev){
    ev.preventDefault();
    ev.returnValue = false;
  })
});

import {formatDate} from '@/utils';
import {mapGetters} from 'vuex';
import PomdMenu from '@/views/gridnt/pomd/menu';

// import {Message} from "element-ui";
// import {doConnect, disConnect} from '@/api/gridnt/websocketservice'
 import {getWarningAllCount}  from '@/api/gridnt/dashboard';

let socketInstance = null;
import {unsubscribeAll} from '@/api/gridnt/websocket';

export default {
  name: 'DsfhHeader',
  components: {PomdMenu},
  mounted() {
    let self = this;
    // doConnect()
    // self.findAllAlarms();
    self.timerInverval = setInterval(() => {
      self.current_time = formatDate(new Date().getTime());
    }, 1000);
  },
  created() {
    this.findAllAlarms()
  },
  destroyed() {
    // disConnect()
    if (this.timerInverval != null) {
      clearInterval(this.timerInverval);
    }
  },
  computed: {
    ...mapGetters(['avatar'])
  },
  data() {
    return {
      timerInverval: null,
      current_time: formatDate(new Date().getTime()),
      username: this.$store.getters.name,
      logo: require('@/assets/images/DsfhHeader/logo.png'),
      centralizedMonitoring:require('@/assets/images/DsfhHeader/centralized-monitoring.svg'),
      farAwayPatrol:require('@/assets/images/DsfhHeader/far-away-patrol.svg'),
      dataMaintenance: require('@/assets/images/DsfhHeader/data-maintenance.svg'),
      intelligentScheduling: require('@/assets/images/DsfhHeader/intelligent-scheduling.svg'),
      panoramicData: require('@/assets/images/DsfhHeader/panoramic-data.svg'),
      databaseMinus: require('@/assets/images/DsfhHeader/database-minus.svg'),
      alarm: require('@/assets/images/DsfhHeader/alarm.svg'),
      user: require('@/assets/images/DsfhHeader/user.svg'),
      off:require('@/assets/images/DsfhHeader/off.svg'),
      alarmCount: 0,
      menuconfig:{
        index:true,
        centralizedMonitoring:true,
        farAwayPatrol:true,
        dataMaintenance:true,
        intelligentScheduling:true,
        panoramicData:true,
        databaseMinus:true
      }
    };
  },
  methods: {
    handleCommand(ref){
      switch (ref)
      {
        case "updatePwd":
          break;
        case "exit":
          this.logout();
          break;
      }

    },
    goToIndex(){
      if(this.menuconfig.index)
      {
        this.$router.push(
          {
            path:'/'
          });
      }
    },
    goToAlert(){
      this.$router.push(
        {
          path:'/alert'
        });
    },
    async logout() {
      let self = this;
      self
        .$confirm('确定注销并退出系统吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(() => {
          // disConnect()
          unsubscribeAll()
          debugger
          self.$store.dispatch('LogOut').then(() => {
            location.href = '/index';
          });
        });
    },
    handleAlarmClick() {
    },
    toUserProfile() {
      this.$router.push('/user/profile');
    },
    findAllAlarms() {
      let self = this;
      getWarningAllCount().then(resp => {
        self.alarmCount = resp.data;
      });
    }
  }
};
</script>


<style scoped>
/*图标旋转90度*/
body{
  background: #090918;
  margin:0;
  font-family: "Hiragino Sans GB","Helvetica Neue",Helvetica,"PingFang SC","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  /*font-family: serif;*/
}

.tabs{
  width: 100%;
  display: flex;
  align-items: center;
  background-color: #032337;
}

/*主菜单按钮样式*/
.button:hover{
  color: #00f4f9;
  background: #026187;
}

/*.button:hover */
.button:hover  .svg-img{
  transform: translateX(-500px);
  filter: drop-shadow(#00f4f9 500px 0);
}

.button {
  background-image: linear-gradient(to right, #03293D 0%, #02415ee0 100%);
  width: 85px;
  min-width: 85px;
  height: 25px;
  transform: skewX(-27deg);
  display: flex;
  align-items: center;
  justify-content: center;
  border-top: 1px solid #00e5f8;
  /* border-left: 1px solid #00e0f399; */
  border-left: 1px solid #018094;
  margin-right: 13px;
  cursor: pointer;
  color: #79a7b8;
  overflow:hidden;

  -moz-user-select: none;
  -webkit-user-select: none;
  -ms-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}

.bttoncilck{
  color: #00f4f9;
  background: #026187;
  /*#91C1E3*/
}

.button>div {
  font-size: 12px;
  transform: skewX(27deg);
  display: flex;
}
.button>div>div{
  margin: auto;
  padding-left: 5px;
}

/*logo图片*/
.tabs-logo{
  height: 43px;
  min-height: 43px;
  /* width: 535px; */
  /* min-width: 535px; */
  width: 41%;
  min-width: 41%;
  margin-right: 70px;
}
.tabs-logo img{
  height: 100%;
  width: 100%;
}

/*告警数量和用户昵称样式*/
.tabs-user{
  display: flex;
  color: #f2f4f6;
  font-size: 12px;
  align-items: center;
  font-weight: 100;
  cursor: pointer;
  font-family: monospace;
}
.tabs-user div{
  width: 55px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.tabs-alarm{
  margin-right: 10px;
}
.tabs-alarm span
{
  background: #ff0000;
  padding: 0px 3px 0px 3px;
  border: 1px solid #f2f4f6;
  border-radius: 30px;
  font-size: 10px;
  min-width: 20px;
  /*font-family: monospace;*/
  position: absolute;
  top: 5px;
  text-align: center;
}
</style>
