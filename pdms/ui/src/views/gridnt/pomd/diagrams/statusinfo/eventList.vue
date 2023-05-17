<template>
  <el-dialog  title="历史数据"
              :visible.sync="dialogVisible"
              width="60%">
    <el-form inline>
      <el-form-item label="对象">
        <el-input readonly v-model="currentF1006Desc"></el-input>
      </el-form-item>
      <el-form-item label="时间范围">
        <el-date-picker
          v-model="queryParams.param.dateValue"
          type="daterange"
          value-format="yyyyMMdd"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item><el-button type="primary"  @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table width="100%" :data="tableList">
      <el-table-column prop="" label="事件时间" :formatter="formatDate"></el-table-column>
      <el-table-column prop="TEXT" label="事描述"></el-table-column>
      <el-table-column prop="VAL" label="值"></el-table-column>

    </el-table>
    <pagination :total="total" :limit="queryParams.page.size" :page="queryParams.page.current"></pagination>
  </el-dialog>
</template>

<script>
import Pagination from '@/components/Pagination';
import * as statusJs from '@/api/gridnt/devicestatus'
export default {
  name: 'eventList',
  components:{Pagination},
  data(){
    return{
      dialogVisible: false,
      tableList:[],
      currentF1006Desc: '',
      total: 0,
      queryParams:{
        page:{
          pageSize:20,
          currentPage:1
        },
        param:{
          dateValue:'',
          currentF1006Code: null
        }
      }
    }
  },
  methods:{
    clearData(){
      this.tableList = []
      this.total = 0
      this.queryParams = {
        page:{
            size:20,
            current:1
        },
        param:{
          dateValue:'',
          currentF1006Code: null
        }
      }
    },
    showDialog(code,desc){
      this.dialogVisible = true
      this.clearData()
      this.currentF1006Desc = desc
      this.queryParams.param.currentF1006Code = code

    },
    loadData(){
      if(this.queryParams.param.dateValue == ''){
        this.$message.error("时间范围不能为空")
        return false
      }
      if(this.queryParams.param.currentF1006Code == null ){
        this.$message.error("必须选中一个信号")
        return false
      }
      statusJs.loadEventHistory(this.queryParams).then((resp) =>{
        this.tableList = resp.data.records
        this.total = resp.data.total
        this.queryParams.page.currentPage = resp.data.pages
        console.log(resp)
      })
    },
    formatDate(row){
      let tm = ''
      if(row.YMD != undefined){
        let ymdStr = row.YMD+''
        tm = ymdStr.substring(0,4)+'-'+ymdStr.substring(4,6)+'-'+ymdStr.substring(6,row.YMD.length)+" "
      }
      if(row.HMSMS != undefined){
        let i = 2;
        let hmsmsStr = row.HMSMS+''
        for(let k = 0;k<=i;k++){
          tm = tm+ hmsmsStr.substring(k *2,(k+1)*2)+(k == i? '.':':')
        }
        tm = tm+ hmsmsStr.substring(6,hmsmsStr.length)
      }
      return tm;
    }
  }
};
</script>

<style scoped>

</style>
