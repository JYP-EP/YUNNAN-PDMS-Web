<template>
  <div>
    <el-input placeholder="请输入地名关键字" v-model="keyword" clearable style="position: absolute;z-index: 999;"></el-input>
    <baidu-map class="bm-view" center="济南" @ready="handler">
      <bm-control>
        <bm-auto-complete v-model="keyword" :sugStyle="{ zIndex: 9999 }" @confirm="selectedLocation">
          <el-input placeholder="请输入地名关键字" v-model="keyword"></el-input>
        </bm-auto-complete>
      </bm-control>
      <bm-local-search :keyword="keyword" :auto-viewport="true" :panel="false"></bm-local-search>
    </baidu-map>
  </div>
</template>

<script>
import BaiduMap from 'vue-baidu-map/components/map/Map.vue';
import bmView from 'vue-baidu-map/components/map/MapView.vue';
import bmControl from 'vue-baidu-map/components/controls/Control.vue';
import bmAutoComplete from 'vue-baidu-map/components/others/AutoComplete.vue';
import bmLocalSearch from 'vue-baidu-map/components/search/LocalSearch.vue';

export default {
  components: {
    BaiduMap,
    bmView,
    bmControl,
    bmAutoComplete,
    bmLocalSearch
  },
  props: {
    address: {
      set(newVal) {}
    },
    withProviceEtc: {
      default() {
        return false;
      }
    }
  },
  computed: {
    keyword: {
      get() {
        return this.address;
      },
      set(newVal) {
        this.$emit('update:address', newVal);
      }
    }
  },
  data() {
    return {
      BMap: null
    };
  },
  methods: {
    handler({ BMap }) {
      this.BMap = BMap;
    },
    selectedLocation(arg) {
      // console.info(arg);
      let lat = arg.target.nd.src.he.lat;
      let lng = arg.target.nd.src.he.lng;
      if (this.withProviceEtc) {
        //逆向解析出省市区等
        let geoc = new BMap.Geocoder();
        geoc.getLocation(arg.target.nd.src.he, rs => {
          let addComp = rs.addressComponents;
          // console.info(addComp.province + ', ' + addComp.city + ', ' + addComp.district + ', ' + addComp.street + ', ' + addComp.streetNumber);
          this.$emit('refreshCoordinate', lat, lng, addComp);
        });
      } else {
        this.$emit('refreshCoordinate', lat, lng);
      }
    }
  }
};
</script>

<style scoped>
.bm-view {
  width: 100%;
  height: 3px;
}

.BMap_noprint anchorTL {
  z-index: 9999;
}
</style>
