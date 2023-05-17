<template>
  <div style="">
    <!--    <substation-cascader style="width: 100%" />-->
    <!--    <el-select ></el-select>-->
<!--    <el-select v-model="curSubStation" placeholder="请选择子站信息" style="width: 100%" ref="substation"-->
<!--               @change="changeSubstation">-->
<!--      <el-option-->
<!--        v-for="dict in substationOptions"-->
<!--        :key="dict.f1041Code"-->
<!--        :label="dict.f1041Name"-->
<!--        :value="dict.f1041Code"-->
<!--      />-->
<!--    </el-select>-->
    <el-input placeholder="输入关键字进行过滤" v-model="filterText" style="margin-bottom: 5px"></el-input>
    <el-tree class="filter-tree"
             :props="defaultProps"
             node-key="id"
             highlight-current
             lazy
             :expand-on-click-node="false"
             :load="loadNode"
             :default-expanded-keys="defaultKey"
             @node-click="handleNodeClick"
             :filter-node-method="filterNode"
             ref="tree"></el-tree>
  </div>
</template>

<script>
import SubstationCascader from '@/views/gridnt/pomd/common/substationCascader';
import {
  getBaysByStation,
  getBaysVolByStation,
  getDQDescList,
  getIedsByBay,
  getSubstations,
  getSubstationsListByDq
} from "@/api/gridnt/ied";

export default {
  name: 'SubstationIedTree',
  components: {SubstationCascader},
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {

  },
  methods: {
    loadNode(node, resolve) {
      if (node.level === 0) {
        this.treeResolve = resolve
        this.rootNode = node
        this.initTreeData(null,resolve,null)
        // if (this.curSubStation) {
        //   this.initTreeData(this.curSubStation, resolve, 'S');
        //   this.$emit('treeSelected', {id:this.subStation.f1041Code,label:this.subStation.f1041Name,type:'S'})
        // } else {
        //   getSubstations().then(resp => {
        //     this.substationOptions = resp.data
        //     if (this.substationOptions.length > 0) {
        //       const station = this.substationOptions[0]
        //       this.curSubStation = station.f1041Code
        //       this.subStation = station
        //       this.initTreeData(this.curSubStation, resolve, 'S');
        //       this.$emit('treeSelected', {id:this.subStation.f1041Code,label:this.subStation.f1041Name,type:'S'})
        //     }
        //   })
        // }

      } else {
        const bayCode = node.data.id
        const bayVol = node.data.label
        const extra =  node.data.extra
        this.initTreeData(bayCode, resolve, node.data.type,bayVol,extra)
      }
    },
    handleNodeClick(data,node) {
      this.$emit('treeSelected', {id:data.id,label:data.label,type:data.type,parent:node.parent})
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    initTreeData(code, resolve, type,bayVol,extra) {
      if(type === 'D'){
        getSubstationsListByDq({dq: extra}).then(resp => {
          if (resp.data) {
            resolve(resp.data)
          }
        })
      }
      if (type === 'S') {
        getBaysVolByStation({f1041Code: code}).then(resp => {
          if (resp.data) {
            resolve(resp.data)
          }
        })
      }
      if (type === 'V') {
        getBaysByStationAndLevel({f1041Code: extra,bayVol:bayVol}).then(resp => {
          if (resp.data) {
            resolve(resp.data)
          }
        })
      } else if (type === 'B') {
        getIedsByBay({bayCode: code}).then(resp => {
          if (resp.data) {
            resolve(resp.data)
          }
        })
      } else if (type === 'I') {
        resolve([])
      }else if(type ===null){
        getDQDescList().then(resp => {
            resolve(resp.data)
            const firstNode = resp.data[0]
            if(firstNode){
              this.defaultKey.push(firstNode.id)
              this.$refs.tree.setCurrentKey(firstNode.id)
              const node =this.$refs.tree.getNode(firstNode.id)
              this.handleNodeClick(node.data,node)
            }
          })
      }
    },
  },

  data() {
    return {
      treeResolve: [],
      rootNode: [],
      filterText: '',
      defaultKey: [],
      defaultProps: {
        children: 'children',
        label: 'label',
        isLeaf: 'leaf'
      }
    };
  }
};
</script>

<style scoped></style>
