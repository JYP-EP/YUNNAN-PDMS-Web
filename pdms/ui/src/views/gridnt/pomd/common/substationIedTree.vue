<template>
  <div style="background-color: transparent">
    <!--    <el-cascader v-show="!showAll"-->
    <!--      style="width: 100%"-->
    <!--      v-model="curSubStation"-->
    <!--      :options="substationOptions"-->
    <!--      :props="{ value: 'id' }"-->
    <!--      @change="changeSubstation"></el-cascader>-->
    <el-tree class="filter-tree"
             :props="defaultProps"
             node-key="id"
             highlight-current
             :expand-on-click-node="false"
             :data="treeData"
             :default-expanded-keys="defaultKey"

             @node-click="handleNodeClick"
             ref="tree"></el-tree>
  </div>
</template>

<script>
import SubstationCascader from '@/views/gridnt/pomd/common/substationCascader';
import {
  getFullIedTree,
  getSubstationsTree,
} from '@/api/gridnt/ied';

export default {
  name: 'SubstationIedTree',
  components: { SubstationCascader },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  props: {
    //是否展示“全网”字段
    showAll: {
      type: Boolean,
      default: false
    }
  },
  created() {
    let code = this.$route.query.code;
    this.$nextTick(() => {
      if(code!=undefined&&code!="")
      {
        this.$store.commit("changeIed",code);
        this.defaultKey = [code];
      }
    });
    this.initTreeData()
  },
  data() {
    return {
      treeData:[],
      treeResolve: [],
      rootNode: [],
      filterText: '',
      defaultKey: [],
      curSubStation: [],
      subStation: {},
      substationOptions: [],
      defaultProps: {
        children: 'children',
        label: 'label',
        isLeaf: 'leaf'
      }
    };
  },
  methods: {
    //下拉框联动
    changeSubstation(ids) {
      this.defaultKey = ids;
      this.$refs.tree.setCurrentKey(ids[1]);
      let node = this.$refs.tree.getNode(ids[1]);
      this.handleNodeClick(node.data, node);
    },
    //获取下拉框数据
    getFilterData() {
      getSubstationsTree().then(resp => {
        this.substationOptions = resp.data;
        if (this.$route.params.dqSubTree == undefined) {
          if (this.substationOptions[0].children != null && this.substationOptions[0].children.length > 0) {
            this.curSubStation = [this.substationOptions[0].id, this.substationOptions[0].children[0].id];
          } else {
            this.curSubStation = [this.substationOptions[0].id];
          }
        }
      });
    },
    initTreeData() {
      let self = this
      getFullIedTree().then(resp=>{
        if(self.showAll){
          self.treeData = [{ label: '全网', id: 'all', type: 'all' ,children:resp.data}]
        }else {
          self.treeData = resp.data
        }
        self.$nextTick(() =>{
          let node = null;
          if(self.$store.state.treeNode.currentNode != null){
            node = self.$store.state.treeNode.currentNode
          }else if(self.$store.state.treeNode.subsCode != null){
            node = self.$refs.tree.getNode(self.$store.state.treeNode.subsCode)
          }else if(self.$store.state.treeNode.iedCode != null) {
            node = self.$refs.tree.getNode(self.$store.state.treeNode.iedCode)
          }
          if(node == null){
            node = self.$refs.tree.getNode(self.treeData[0].id)
          }
          self.$refs.tree.setCurrentKey(node.data.id)
          self.handleNodeClick(node.data,node)
          self.defaultKey = [node.data.id]
        })
      })
    },
    handleNodeClick(data, node) {
      this.$store.commit("changeCurrentNode",node);
      if(data.type =='S'){
        this.$store.commit("changeSubsCode",data.id);
      }else if(data.type =='I'){
        this.$store.commit("changeIed",data.id);
      }
      let sendData = Object.assign({}, data);
      sendData.parent = Object.assign({}, node.parent);
      this.$emit('treeSelected', sendData);
    },
    setTree(ids)
    {
      this.$nextTick(() => {
        this.$store.commit("changeIed",ids);
        this.defaultKey = [ids];
      });
    }
  },
};
</script>

<style scoped>
.el-tree{
  background-color: transparent;
  color: #fff;
}
/deep/.el-tree-node__content:hover {
  background-color: #141e4e;
}
/deep/.el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content{
  background-color: #141e4e;
}
/deep/.el-tree-node:focus > .el-tree-node__content {
  background-color: #141e4e;
}
</style>
