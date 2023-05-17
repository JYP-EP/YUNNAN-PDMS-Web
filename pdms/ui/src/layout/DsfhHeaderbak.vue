<template>
  <div class="head-title">
    <img :src="spacer" class="spacer-img"/>
    <div style="display: flex; flex-direction: row; width: 100%; z-index: 999">
      <div>
        智能变电站保护设备在线监视与诊断系统
      </div>
      <pomd-menu style="margin-left:50px;"></pomd-menu>

      <div class="head-right">
        <div class="alarm-div" :style="alarmCount <= 0 ? 'color:white' : 'color:red'" @click="handleAlarmClick">
          <span style="margin: auto 5px auto 5px;" @click="goToAlert">告警提示({{ alarmCount }})</span>
        </div>
        <span class="iconfont icon-setting1" style="font-size: 25px;"></span>
        <span @click="toUserProfile" style="cursor: pointer;">
          <img :src="avatar" class="user-avatar"/>
          <span>{{ username }}</span>
        </span>
        <span class="head-timer">{{ current_time }}</span>
        <span @click="logout" style="cursor: pointer;color: #ce6454;">
          <span class="iconfont icon-off" style="font-size: 20px;margin-right: 5px;"></span>
          <span>退出</span>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
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
      spacer: require('@/assets/images/gridnt/spacer.png'),
      alarmCount: 0
    };
  },
  methods: {
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
.head-title {
  height: 90px;
  text-align: center;
  line-height: 90px;
  font-size: 30px;
  background-size: 100% 100%;
  color: #28c8f9;
  font-weight: bold;
  padding-left: 30px;
  display: flex;
}

.head-timer {
  color: #dee4ed;
  font-size: 21px;
  /*flex: 3;*/
  display: flex;
  align-items: flex-start;
}

.head-right {
  font-size: 16px;
  line-height: 90px;
  height: 90px;
  display: flex;
  flex-direction: row;
  flex: 4;
  justify-content: flex-end;
}

.head-right input {
  display: inline-block;
  margin: auto 30px auto auto;
  height: 35px;
  line-height: 35px;
  padding-left: 30px;
  width: 40%;
  background-color: transparent;
  border: 1px solid #fff;
  border-radius: 25px;
}

.head-right input:focus,
.head-right input:hover,
.head-right input:focus-visible {
  outline: none;
}

.avatar-wrapper {
  width: 150px;
  height: 30px;
  color: #fff;
  font-size: 14px;
}

.user-avatar {
  width: 25px;
  height: 25px;
  border-radius: 12px;
  margin-right: 5px;
  vertical-align: middle;
}

.head-right > span {
  display: inline-block;
  margin-right: 30px;
  color: #fff;
}

.spacer-img {
  display: inline-block;
  position: absolute;
  width: 100%;
  height: 90px;
  left: 0;
  right: 0;
  z-index: 1;
}

.alarm-div {
  display: inline-block;
}

.alarm-div:hover {
  cursor: pointer;
}

.alarm-png {
  width: 25px;
  height: 25px;
  vertical-align: middle;
}
</style>
