<template>
  <div id="main-map"></div>
</template>

<script>
import * as _ from 'lodash';

export default {
  name: 'MainMap',
  mounted() {
    this.initMap();
  },
  data() {
    return {
      map: null,
      substations: [
        {
          name: '南郊变电站',
          point: new BMap.Point(115.48789, 35.206376)
        },
        {
          name: '龙堌变电所',
          point: new BMap.Point(115.896402, 35.29376)
        }
      ]
    };
  },
  methods: {
    initMap() {
      this.map = new BMap.Map('main-map');
      // 创建地图实例
      let point = new BMap.Point(115.48789, 35.206376);
      // 创建点坐标
      this.map.centerAndZoom(point, 11);
      this.map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
      this.getSubstation();
    },
    getSubstation() {
      _.forEach(this.substations, substation => {
        let image = require('@/assets/images/gridnt/dashboard/alarmpoint3.png');
        let myIcon = new BMap.Icon(image, new BMap.Size(44, 50));
        // 创建Marker标注
        let marker = new BMap.Marker(substation.point, {
          icon: myIcon
        });
        // 在地图上添加点标记
        this.map.addOverlay(marker);
        marker.addEventListener('click', () => {
          this.$router.push('/substation');
        });
      });
    }
  }
};
</script>

<style scoped>
#main-map {
  width: 100%;
  height: 100%;
  height: calc(100% - 50px);
  position: relative;
}

/deep/ .BMap_Marker > div > img {
  width: 100%;
  height: 100%;
}
</style>
