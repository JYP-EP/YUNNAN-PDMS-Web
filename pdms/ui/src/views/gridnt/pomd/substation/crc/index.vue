<template>
  <div class="app-container">
  <el-table :data="tableData" style="width: 100%">
    <el-table-column align="center" prop="iedName" label="Ied名称"></el-table-column>
    <el-table-column align="center" prop="scdCrc" label="SCD中CRC"></el-table-column>
    <el-table-column align="center" prop="iedCrc" label="装置上送CRC"></el-table-column>
    <el-table-column align="center" prop="consistent" label="结果" :formatter="formatConsistent"></el-table-column>
  </el-table>
  <pagination
    v-show="total>0"
    :total="total"
    :page.sync="queryParams.page.current"
    :limit.sync="queryParams.page.size"
    @pagination="initCrcData"
  />
  </div>
</template>

<script>
import {getCrcData} from "@/api/gridnt/ied";
import Pagination from '@/components/Pagination'

export default {
  name: 'CrcTable',
  components:{Pagination},
  data() {
    return {
      queryParams: {
        page: {
          current: 1,
          size: 15,
        },
        entity: {

        }
      },
      total: 0,
      tableData: [
        {
          ied: 'PL2201A',
          crc: 'ASDFASDFASDFASDF',
          crc2: 'ASDFASDFASDFASDd',
          result: '不一致'
        },
        {
          ied: 'PL2201B',
          crc: 'ASDFASDFASDFASDF',
          crc2: 'ASDFASDFASDFASDd',
          result: '不一致'
        },
        {
          ied: 'PL2202B',
          crc: 'ASDFASDFASDFASDF',
          crc2: 'ASDFASDFASDFASDd',
          result: '不一致'
        },
        {
          ied: 'PL2202A',
          crc: 'ASDFASDFASDFASDF',
          crc2: 'ASDFASDFASDFASDd',
          result: '不一致'
        }
      ]
    };
  },
  created() {
    this.initCrcData()
  },
  methods:{
    formatConsistent(row){
      return row.scdCrc===row.iedCrc?'一致':'不一致';
    },
    initCrcData(){
      getCrcData(this.queryParams).then(resp=>{
        this.tableData = resp.data.records;
        this.total = resp.data.total;
      })
    }
  }
};
</script>

<style scoped></style>
