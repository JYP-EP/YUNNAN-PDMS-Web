const treeNode={
  state: {
    currentNode: null,  //当前选中节点信息 substationIedTree
    subsCode: null,　　　//当前选中站所Code f1041Code
    iedCode: null　　　　//当前选中iedCode  f1046Code
  },
  mutations:{
    changeCurrentNode(state,node){
      state.currentNode = node
    },
    changeSubsCode(state,subsCode){
      state.subsCode = subsCode
    },
    changeIed(state,iedCode){
      state.iedCode = iedCode
    }
  }
}

export default treeNode
