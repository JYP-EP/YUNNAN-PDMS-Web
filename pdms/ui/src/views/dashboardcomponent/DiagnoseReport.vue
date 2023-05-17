<template>
  <div style="cursor: default;margin:9px 10px 18px">
    <div class="table-header table-row">
      <div class="table-cell" style="width: 10%;text-align: center">序号</div>
      <div class="table-cell" style="width: 30%;text-align: center">站点</div>
      <div class="table-cell" style="width: 10%;text-align: center">回</div>
      <div class="table-cell" style="width: 10%;text-align: center">状</div>
      <div class="table-cell" style="width: 10%;text-align: center">采</div>
      <div class="table-cell" style="width: 10%;text-align: center">定</div>
      <div class="table-cell" style="width: 10%;text-align: center">异</div>
      <div class="table-cell" style="width: 10%;text-align: center">跳</div>
    </div>
    <div class="table-body">
      <div>
        <div
          class="table-row"
          :class="{ hasBgc: index % 2 === 0 }"
          v-for="(item, index) in data"
          :key="index"
          :ref="'row_' + index"
        >
          <div class="table-cell" style="width: 10%;text-align: center">{{index+1}}</div>
          <div class="table-cell" style="width: 30%" :title="item.f1041DESC"   @click="goToAlert()">
            {{ item.f1041DESC }}
          </div>
          <div class="table-cell" style="width: 10%;text-align: center" :title="item.acount"  @click="goToAlert()">{{ item.acount }}</div>
          <div class="table-cell" style="width: 10%;text-align: center" :title="item.bcount"  @click="goToAlert()">{{ item.bcount }}</div>
          <div class="table-cell" style="width: 10%;text-align: center" :title="item.ccount"  @click="goToAlert()">{{ item.ccount }}</div>
          <div class="table-cell" style="width: 10%;text-align: center" :title="item.dcount"  @click="goToAlert()">
            {{ item.dcount }}
          </div>
          <div class="table-cell" style="width: 10%;text-align: center" :title="item.ecount"  @click="goToAlert()">
            {{ item.ecount }}
          </div>
          <div class="table-cell" style="width: 10%;text-align: center" :title="item.fcount">
            {{ item.fcount }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DiagnoseReport",
  props: {
    data: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  data() {
    return {
      initMt: 0,
      // getPlayData:[],
      visible: true,
      stop: false,
    };
  },
  methods: {
    goToAlert(){
      this.$router.push(
        {
          path:'/alert'
        });
    }
  },
  watch: {

  },
  computed: {
  },
  mounted() {
    // this.play();
  },
}
</script>

<style lang="scss" scoped>
.table-row {
  display: flex;
  line-height: 35px;
  height: 35px;
  transition: all 0.3s;
  border-bottom: 1px solid rgba(63, 88, 114, 1);
}
.table-header {
  color: rgba(87, 150, 190, 1);
}
.table-cell {
  text-align: center;
  font-size: 12px;
  text-overflow: ellipsis;
  overflow: hidden;
}
.table-body {
  height: 200px;
  overflow-y: auto;
  .table-row {
    color: #fff;
  }
}
::-webkit-scrollbar {
  width: 6px;
}
::-webkit-scrollbar-thumb {
  background-color: #5796be;
  border-radius: 10px;
}
::-webkit-scrollbar-track {
  border-radius: 10px;
  background-color: #fff;
}
</style>
