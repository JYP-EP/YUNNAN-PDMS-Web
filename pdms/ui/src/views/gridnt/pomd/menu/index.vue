<template>
  <div>
    <el-menu
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#212936"
      text-color="#fff"
      :default-active="activeIndex2"
      active-text-color="#ffd04b"
    >
<!--      <el-menu-item index="monitor">智能运维</el-menu-item>-->
<!--      <el-menu-item index="index">智能告警</el-menu-item>-->
      <el-menu-item v-if="menuconfig.index" index="index" >首页</el-menu-item>
      <el-menu-item v-if="menuconfig.substation" index="substation" >状态监测</el-menu-item>
      <el-menu-item v-if="menuconfig.commStatus" index="commStatus" >通信状态</el-menu-item>
      <el-menu-item v-if="menuconfig.monitor" index="monitor">智能运维</el-menu-item>
      <el-menu-item v-if="menuconfig.alert" index="alert">运维文件</el-menu-item>
      <el-menu-item v-if="menuconfig.constantValue" index="constantValue">定值校核</el-menu-item>
      <el-menu-item v-if="menuconfig.Faultanalysis" index="Faultanalysis">故障分析</el-menu-item>
    </el-menu>
  </div>
</template>

<script>

export default {
  name: 'PomdMenu',
  data() {
    return {
      activeIndex2: '',
      menuconfig:{
        index:true,
        substation:true,
        commStatus:true,
        monitor:true,
        alert:true,
        constantValue:true,
        Faultanalysis:true
      }
    };
  },
  watch:{
    $route(data){
      this.activeIndex2 = data.path.substring(1,data.path.length);
    }
  },
  created() {

    this.activeIndex2 = window.WebConfig.menuconfig.activeIndex2;



    this.menuconfig = window.WebConfig.menuconfig;


    let path = this.$route.path
    this.activeIndex2 = path.substring(1,path.length);
  },
  methods: {
    handleSelect(key, keyPath) {
      if(this.$route.path.indexOf(key)===-1){
        this.$router.push({
          path: key,
          params: {data: 'query' }
        })
      }
      console.log(key, keyPath);
    }
  }
};
</script>
