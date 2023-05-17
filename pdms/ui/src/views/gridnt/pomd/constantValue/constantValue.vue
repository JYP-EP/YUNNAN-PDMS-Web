<template>
  <div style="height: 100%;width: 100%;">
    <el-container style="height: 100%;">
      <el-aside width="350px" style="background-color: #061029;;padding: 0px; margin-right: 10px; height: 100%">
        <substation-ied-tree @treeSelected="selectedIed" ref="substationTree" />
      </el-aside>
      <el-container style="background-color: #030d28;">
        <el-main style="padding: 5px; background-color: transparent">
          <div v-if="currentNode.type != 'I'"　class="warning-div">
            <i class="el-icon-error" style="color: red;margin-right: 5px;"></i>
            请选择设备
          </div>
          <el-tabs  v-else>
            <el-tab-pane>
              <span slot="label"> 保护定值</span>
              <el-row :gutter="10" class="constant-value-desc">
                <el-col :span="8" ><span>LD实例名: {{ld}}</span></el-col>
                <el-col :span="8" ><span  >所属数据集: {{className}}</span></el-col>
                <el-col :span="8"><span  >当前定值区号: {{valueArea}}</span></el-col>
              </el-row>
              <el-tabs v-model="valueArea" class="child-tab">
                <el-tab-pane
                  v-for="(item, index) in grpList"
                  :key="item.value"
                  :label="item.name"
                  :name=item.value
                  @click="refreshTableData"
                >
                </el-tab-pane>
              </el-tabs>
              <el-table
                :data="dsSettingTable"
                ref="table"
                :key="tableKey" :height="settingTableHeight">
                <el-table-column label="序号" width="50px" align="center" type="index"></el-table-column>
                <el-table-column
                  prop="desc"
                  label="描述"
                  width="180">
                </el-table-column>
                <el-table-column
                  label="值">
                  <template slot-scope="scope">
                    {{getGrpValue(scope.row.valList)}}
                  </template>
                </el-table-column>
                <el-table-column
                  prop="type"
                  label="数据类型">
                </el-table-column>
                <el-table-column
                  prop="unit"
                  label="单位">
                </el-table-column>
                <el-table-column
                  prop="min"
                  label="最小值">
                </el-table-column>
                <el-table-column
                  prop="max"
                  label="最大值">
                </el-table-column>
                <el-table-column
                  prop="step"
                  label="步长">
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane>
              <span slot="label"> 软压板</span>
              <el-row :gutter="10" class="constant-value-desc" style="margin-bottom: 15px;">
                <el-col :span="8"><span>LD实例名: {{dsRelayEnaLd}}</span></el-col>
                <el-col :span="8"><span>所属数据集: {{dsRelayEnaClassName}}</span></el-col>
              </el-row>
              <el-table
                :data="dsRelayEnaTable"
                height="calc(100vh - 190px)">
                <el-table-column label="序号" width="50px" align="center">
                  <template slot-scope="scope">
                    {{ scope.$index + 1 }}
                  </template>
                </el-table-column>
                <el-table-column
                  prop="desc"
                  label="描述"
                  width="180">
                </el-table-column>
                <el-table-column
                  label="值">
                  <template slot-scope="scope">
                    {{getGrpValue(scope.row.valList)}}
                  </template>
                </el-table-column>
                <el-table-column
                  prop="type"
                  label="数据类型">
                </el-table-column>
                <el-table-column
                  prop="unit"
                  label="单位">
                </el-table-column>
                <el-table-column
                  prop="min"
                  label="最小值">
                </el-table-column>
                <el-table-column
                  prop="max"
                  label="最大值">
                </el-table-column>
                <el-table-column
                  prop="step"
                  label="步长">
                </el-table-column>
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import SubstationIedTree from '@/views/gridnt/pomd/common/substationIedTree';
import {getSettingFileData} from '@/api/gridnt/settingFile';
export default {
  name: "constantValue",
  components: {SubstationIedTree},
  data() {
    return {
      valueArea: "",
      valueAreaList:[],
      dsSetting:{},
      dsRelayEna:{},
      dsParameter:{},
      tableData:[],
      dsSettingTable:[],
      grpList:[],
      currentNode:{},
      ld: '',
      className: '',
      grp: '',
      dsRelayEnaLd:'',
      dsRelayEnaClassName: '',
      dsRelayEnaGrp: '',
      dsRelayEnaTable:[],
      activeName: 'tab0',
      substation: {},
      tableKey: Math.random(),
      // settingTableHeight:'calc(100vh - 200px)'
    };
  },
  computed:{
    settingTableHeight:function(){
      if(this.grpList.length > 0){
        return 'calc(100vh - 230px)'
      }else{
        return 'calc(100vh - 190px)'
      }
    }
  },
  created() {
    this.getSettingFile();
  },
  methods:{
    refreshTableData(){
      this.dsSettingTable = this.dsSettingTable
      this.tableKey = Math.random()
    },
    getGrpValue(list){
      for (let i = 0; i < list.length; i++) {
        if(list[i].grp === this.valueArea){
          return list[i].value
          break;
        }
      }
    },
    getSettingFile(code){
      getSettingFileData({
        code: code
      }).then(res=>{
        if(res.data == null){
          this.dsSettingTable = []
          this.grpList = []
          this.ld = ''
          this.className = ''
          this.valueArea = ''
          this.dsRelayEnaLd = ''
          this.dsRelayEnaClassName = ''
          this.dsRelayEnaGrp = ''
          this.dsRelayEnaTable = []
        }else {
          this.dsSettingTable = res.data.dsSetting.siList;
          this.ld =  res.data.dsSetting.ld
          this.className =  res.data.dsSetting.className
          this.valueArea = res.data.dsSetting.grp;
          this.grpList = res.data.dsSetting.grpList
          this.dsRelayEnaTable = res.data.dsRelayEna.siList
          this.dsRelayEnaLd = res.data.dsRelayEna.ld
          this.dsRelayEnaClassName = res.data.dsRelayEna.className
          this.dsRelayEnaGrp = res.data.dsRelayEna.grp
          this.dsRelayEna = res.data.dsRelayEna;
          this.dsParameter = res.data.dsParameter;
        }
      })
    },
    handleClick(){},
    selectedIed(node) {
      this.activeClass = 'A';
      this.currentNode = node;
      switch (node.type) {
        case 'I':
          this.ied = node;
          this.$nextTick(() => {
            //this.$refs['circuit'].loadData(this.ied);
            this.getSettingFile(node.id)
          });
          break;
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.tagWrapper {
  height: 40px;

  .el-tag {
    margin: 0px 15px;
    cursor: pointer;
  }
}

.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

.active, .inactive {
  cursor: pointer;
}

.active {
  color: #409eff;
  border: 1px solid #d9ecff;
}
.constant-value-desc{
  font-size: 14px;
  color: #fff;
}
::v-deep .child-tab .el-tabs__item{
    font-size: 12px;
  }
.warning-div{
  width: 100%;
  text-align: center;
  color: #fff;
  height: 50px;
  line-height: 50px;
}
</style>
