import * as joint from 'jointjs';
import * as circuitAndReality from '@/api/gridnt/draw/circuitAndReality';
import {  CableModel, CirIed, plateModel, plateWidth } from '@/api/jointjs/defineElement';

/**
 * 虚实结合虚回路
 * @type {number}
 */
let pagePadding = 60,iedWidth = 300,iedInitHeight = 100,cirRectMargin = 10,cirIedWidth = 300,iedPadding = 10,
  plateXMargin = 20
export function drawCircuit( titlePosY,pageWidth,tb1065Data,cirAndPlate,iedAddHeight,graph,statusDesc,cirRectHeight,
                             subsData,subsMap){
  let cirContainerCell = graph.getCell('cir-container')
  if(cirContainerCell != null){
    cirContainerCell.remove();
  }
  cirContainerCell =  new joint.shapes.basic.Rect({
    id:'cir-container',
    position:{x:pagePadding,y:titlePosY},
    size:{width:pageWidth,height: 0}
  })
  graph.addCell(cirContainerCell)
  let titleCell = drawDialogItemTitle(new joint.shapes.standard.TextBlock(),
    pagePadding, titlePosY , '二次回路（虚）', graph)
  cirContainerCell.embed(titleCell)
  let iedY = titlePosY+ titleHeight + 10
  let sendIed = tb1065Data['F1046_CODE_IEDSEND'];
  let receiveIed = tb1065Data['F1046_CODE_IEDRECV'];
  let iedRecvX = pageWidth - pagePadding - iedWidth
  let sendIedCell = drawCirIed(sendIed, tb1065Data['F1046_DESC_IEDSEND'], pagePadding,iedY,iedWidth,iedInitHeight);
  let recvIedCell = drawCirIed(receiveIed, tb1065Data['F1046_DESC_IEDRECV'],
    iedRecvX,iedY,iedWidth,iedInitHeight);
  cirContainerCell.embed([sendIedCell, recvIedCell])
  graph.addCells(sendIedCell, recvIedCell);
  let cirRectSendX = pagePadding + iedPadding;
  let cirRectRecvX = iedRecvX + iedPadding;
  let topLink = null;
  let cells = [], plates = [];
  for (let i = 0; i < cirAndPlate.length; i++) {
    let cirPlateItem = cirAndPlate[i];
    let cirList = cirPlateItem.list;
    //画虚回路
    let rectInitY = recvIedCell.position().y +80
    for (let j = 0; j < cirList.length; j++) {
      let cirItem = cirList[j];
      let rectY = rectInitY  + (cirRectHeight + cirRectMargin) * cells.length / 3;
      let rectWidth = iedWidth - 2*iedPadding
      let rectSize = {width:rectWidth,height:cirRectHeight}
      let sendRect = drawCirRect(cirItem['f1061DescPsend'], rectSize,cirRectSendX, rectY);
      let receiveRect = drawCirRect(cirItem['f1062DescPrecv'],rectSize, cirRectRecvX, rectY);
      let circuitLine = circuitAndReality.drawLink(sendRect, receiveRect, '', '');
      cirContainerCell.embed([sendRect, receiveRect])
      cells.push(sendRect, receiveRect,circuitLine);
      if (i == 0 && j == 0) {
        topLink = circuitAndReality.drawAppIdLine(sendRect, receiveRect, 'top', 'APPID:' + tb1065Data['F1065_APPID'] + ';状态：'+statusDesc,tb1065Data);
      }
    }
    //画软压板
    if (cirPlateItem.plate != undefined && (cirPlateItem.type == 'send' || cirPlateItem.type == 'recv')) {
      let firstCell = cells[cells.length - (3 * cirList.length)];
      let height = (cirList.length - 1) * (cirRectMargin + cirRectHeight) + cirRectHeight;
      let plateY = firstCell.position().y;
      let plateX = 0;
      if ('send' == cirPlateItem.type) {
        plateX = sendIedCell.position().x + cirIedWidth + plateXMargin;
      } else {
        plateX = recvIedCell.position().x - plateWidth - plateXMargin;
      }
      let plateCell = new plateModel({
        position: { x: plateX, y: plateY },
        size: { height: height }
      });
      plateCell.prop('attrs/body/fill', '#003363').prop('attrs/body/stroke', '#3ca5bd').prop('attrs/circle1/stroke', '#3ca5bd').prop('attrs/circle2/stroke', '#3ca5bd')
        .prop('attrs/line/stroke', '#3ca5bd').prop('attrs/line2/stroke', '#3ca5bd');
      plates.push(plateCell);
      cirContainerCell.embed(plateCell)
      //压板数据订阅
      subsData.push({ 'table': 'status', key: cirPlateItem.plate, cols: ['value'] });
      subsMap[cirPlateItem.plate] = { value: 1, cell: plateCell };
    }
  }
  sendIedCell.resize(iedWidth, iedAddHeight);
  recvIedCell.resize(iedWidth, iedAddHeight);
  if(cells.length > 0){
    var bottomLink = circuitAndReality.drawAppIdLine(cells[cells.length - 3],
      cells[cells.length - 2], 'bottom', '',tb1065Data);
    cells.push(topLink, bottomLink);
  }
  cells.push(plates)
  graph.addCells(cells);
}
function drawCirIed(iedCode, showDesc, x,y,width,height) {
  let ied = new CirIed({
    id: 'cir'+iedCode,
    position: { x: x, y: y },
    size: { width: width, height: height },
    attrs: {
      text1: { text: showDesc }
    }
  });
  return ied;
}

function drawCirRect(showDesc, size,x, y) {
  let cirRect = new joint.shapes.basic.Rect({
    // id: this.mainIedInfo.id,
    position: { x: x, y: y },
    size: size,
    attrs: {
      text: { text: showDesc, fill: '#4fbad4' },
      rect: { stroke: '#3ca5bd', 'stroke-width': 1, fill: '#003363' }
    }
  });
  return cirRect;
}

/**
 * 虚实结合物理链接图
 * @type {number}
 */
let phyIedWidth =  250,phyIedPadding = 10
export function  drawSinglePhysical(pageWidth,item, posY,graph) {
  let cableCells = []
  let iedACell = drawPhyIedCell(item,posY,150)
  let iedBCellX = pageWidth - pagePadding - phyIedWidth;
  let iedBCell = iedACell.clone().prop("id",'rel-'+item['f1046CodeB'])
    .position(iedBCellX, posY).prop('attrs/text1/text', item['f1046NameB']).prop('attrs/text2/text', item['f1046DescB']);
  let cableWidth = phyIedWidth - 2 * phyIedPadding
  let cableACell = new CableModel({
    id:'cable-'+item['f1046CodeA'],
    position: { x: pagePadding + phyIedPadding, y: posY + 90 },
    size: { width: cableWidth},
    attrs: {
      text1: { text: item['f1048NoA'] }
    }
  });
  let cabelBCell = cableACell.clone().set("id",'cable-'+item['f1046CodeB']).position(iedBCellX + phyIedPadding, posY + 90)
    .prop('attrs/text1/text', item['f1048NoB']).prop('attrs/text2/refX', '60%');
  cableCells.push(cableACell,cabelBCell)
  // let link = circuitAndReality.drawLink(cableACell, cabelBCell,
  //   item['f1051Name'] + ':' + item['f1052No'], '状态：数据无效');
  graph.addCells(iedACell, iedBCell, cableACell, cabelBCell);
  return cableCells;
}

function drawPhyIedCell(item,posY,height) {
  let cell = new CirIed({
    id:'rel-'+item['f1046CodeA'],
    position: { x: pagePadding, y: posY },
    size: { width: phyIedWidth, height: height },
    attrs: {
      text1: { text: item['f1046NameA'], refY: '30' },
      text2: { text: item['f1046DescA'], refY: '50' }
    }
  });
  return cell;
}

/**
 * 虚实结合多条物理链路
 * @type {number}
 */
let cableMarginTop = 50
export function drawMultiplePhysical(relData, posY,physicalMargin,graph,subsData,subsMap) {
  let cableWidth = phyIedWidth - 2 * phyIedPadding
  let cells = []
  let codeA = relData['f1046CodeA'];
  let codeACell = graph.getCell('rel-' + codeA);
  //第一个
  if (codeACell == null) {
    codeACell = drawPhyIedCell(relData,posY,200)
    cells.push(codeACell)
  }
  let cableX = codeACell.position().x + phyIedPadding
  let cableCell2 = null;
  let addTopCable = true
  if (codeACell.getEmbeddedCells().length > 0) {
    let cableCell1 = codeACell.getEmbeddedCells()[0]
    let cablePosition = cableCell1.get("cablePosition")
    if('top' == cablePosition){
      addTopCable = false
      cableCell2 = cableCell1.clone().translate(0,cableMarginTop).set("cablePosition","bottom")
        .prop('attrs/text2/refX', '60%').prop('attrs/text1/text', relData['f1048NoA'])
    }
  }
  if(addTopCable){
    cableCell2 = new CableModel({
      cablePosition:'top',
      position: { x: cableX, y: posY + 90 },
      size: { width: cableWidth },
      attrs: {
        text1: { text: relData['f1048NoA'] }
      }
    });
    //第一个“光强”放到左边，其他的都放到右边
    if(cells.length == 0){
      cableCell2.prop('attrs/text2/refX', '60%')
    }
    codeACell.embed(cableCell2)
  }
  cells.push(cableCell2)
  let iedBCell = codeACell.clone().translate(physicalMargin + phyIedWidth ,0)
    .set("id","rel-"+relData['f1046CodeB'])
    .prop('attrs/text1/text', relData['f1046NameB']).prop('attrs/text2/text', relData['f1046DescB']);
  let cabelBCell = cableCell2.clone().position(iedBCell.position().x+phyIedPadding,cableCell2.position().y)
    .prop('attrs/text1/text', relData['f1048NoB']).prop('attrs/text2/refX', '60%');
  iedBCell.embed(cabelBCell)
  let f1051Name = joint.util.breakText(relData['f1051Name'],{width:physicalMargin})
  let link = circuitAndReality.drawLink(cableCell2, cabelBCell,
    f1051Name + ':' + relData['f1052No'], '状态：数据无效');
  cells.push(iedBCell,cabelBCell,link)
  graph.addCells(cells)
  addPhysicsSubsData(subsData,subsMap,cableCell2,cabelBCell,link,relData)
}

export function addPhysicsSubsData(subsList,subsMap,cableACell,cableBCell,realityItem){
  if(realityItem['f1006CodeA'] != undefined){
    subsList.push({"table":"analog","key":realityItem['f1006CodeA'],cols:['value']})
    subsMap[realityItem['f1006CodeA']] = {"value":"--","cell":cableACell}
  }
  if(realityItem['f1006CodeB'] != undefined){
    subsList.push({"table":"analog","key":realityItem['f1006CodeB'],cols:['value']})
    subsMap[realityItem['f1006CodeB']]={"value":"--","cell":cableBCell}
  }

}


let titleWidth = 300,titleHeight = 30

export function drawDialogItemTitle(textBlock,posX,posY,desc,graph){
  // var textBlock = new joint.shapes.standard.TextBlock();
  textBlock.resize(titleWidth, titleHeight);
  textBlock.position(posX, posY);
  textBlock.attr('body/fill', 'transparent');
  textBlock.attr('body/stroke-width', '0');
  textBlock.attr('label/text', desc);
// Styling of the label via `style` presentation attribute (i.e. CSS).
  textBlock.attr('label/style/color', '#4fbad4');
  textBlock.attr('label/style/justify-content', 'left');
  textBlock.addTo(graph);
  return textBlock
}

export  {titleHeight }
