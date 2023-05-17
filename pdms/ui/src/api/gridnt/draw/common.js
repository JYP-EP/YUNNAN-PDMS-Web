
/**
 * 计算主Ied的高度
 * @param allData
 * @param mainIedY
 * @param graph
 * @param mainIedCell
 * @constructor
 */
export function ResizeMainIed(allData,mainIedY,graph,mainIedCell){
  //判断两边Ied总高度，取最高的作为mainIED的高度
  let leftList = allData[0]
  let leftHeight = 0
  if(leftList.length > 0){
    let lastCell = graph.getCell(leftList[leftList.length -1].iedInfo.f1046Code)
    let lastCellY = lastCell.position().y
    leftHeight = lastCellY - mainIedY + lastCell.size().height
  }
  let rightList = allData[1]
  let rightHeight = 0
  if(rightList.length > 0){
    let lastCell = graph.getCell(rightList[rightList.length -1].iedInfo.f1046Code)
    let lastCellY = lastCell.position().y
    rightHeight = lastCellY - mainIedY + lastCell.size().height
  }
  let mainResizeHeight = leftHeight > rightHeight? leftHeight:rightHeight
  mainIedCell.resize(mainIedCell.size().width, mainResizeHeight);
}


