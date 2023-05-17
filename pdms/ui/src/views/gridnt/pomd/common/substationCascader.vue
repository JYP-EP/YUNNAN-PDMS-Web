<template>
  <el-cascader v-model="value" :options="options" :show-all-levels="false"></el-cascader>
</template>
<script>
export default {
  name: 'SubstationCascader',
  data() {
    return {
      value: ['sd', 'hz', 'nj'],
      options: [
        {
          value: 'sd',
          label: '山东',
          children: [
            {
              value: 'hz',
              label: '菏泽',
              children: [
                {
                  value: 'nj',
                  label: '南郊变电站'
                },
                {
                  value: 'fankui',
                  label: '龙堌变电所'
                }
              ]
            },
            {
              value: 'daohang',
              label: '济南',
              children: [
                {
                  value: 'cexiangdaohang',
                  label: '工业园变电站'
                },
                {
                  value: 'dingbudaohang',
                  label: '解放路变电站'
                }
              ]
            }
          ]
        }
      ]
    };
  }
};
</script>
