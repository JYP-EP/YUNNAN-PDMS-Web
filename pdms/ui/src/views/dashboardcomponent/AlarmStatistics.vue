<template>
    <div style="cursor: default;margin:19px 10px 18px">
      <div class="table-header table-row">
        <div class="table-cell" style="width: 10%;text-align: center">序号</div>
        <div class="table-cell" style="width: 34%">站所名称</div>
        <div class="table-cell" style="width: 33%">告警类型</div>
        <div class="table-cell" style="width: 33%;text-align: center">数量</div>
      </div>
      <div class="table-body">
        <div
          class="table-row"
          :class="{ hasBgc: index % 2 === 0 }"
          v-for="(item, index) in data"
          :key="index"
          :ref="'row_' + index"
        >
          <div class="table-cell" style="width: 10%;text-align: center">{{index+1}}</div>
          <div class="table-cell" style="width: 34%" :title="item.substationName">
            {{ item.substationName }}
          </div>
          <div class="table-cell" style="width: 33%" :title="item.type">{{ item.type }}</div>
          <div class="table-cell" style="width: 33%;text-align: center" :title="item.warningNumber" @click="goToAlert()">
            {{ item.warningNumber }}
          </div>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: "alarmStatistics",
  props: {
    data: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  methods:{
    goToAlert(){
      this.$router.push(
        {
          path:'/alert'
        });
    }
  }
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
