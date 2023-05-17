<template>
    <div style="cursor: default;margin:19px 10px 18px">
      <p style="color: #0e95d2"> 未巡视站所列表</p>
      <div class="table-header table-row">
        <div class="table-cell" style="width: 34%">站所名称</div>
        <div class="table-cell" style="width: 33%">站所描述</div>
        <div class="table-cell" style="width: 33%;text-align: center">是否巡检</div>
      </div>
      <div class="table-body">
        <div
          class="table-row"
          :class="{ hasBgc: index % 2 === 0 }"
          v-for="(item, index) in data"
          :key="index"
          :ref="'row_' + index"
        >
          <div class="table-cell" style="width: 34%" :title="item.f1041Name">
            {{ item.f1041Name }}
          </div>
          <div class="table-cell" style="width: 33%" :title="item.f1041Desc">{{ item.f1041Desc }}</div>
          <div class="table-cell" style="width: 33%;text-align: center" :title="item.visit">
            否
          </div>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: "NotVisitStation",
  props: {
    data: {
      type: Array,
      default: () => {
        return [];
      },
    },
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
