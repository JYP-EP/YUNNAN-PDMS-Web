import * as joint from 'jointjs';
import { LogicBlockModel } from '@/api/jointjs/defineCb';
export function drawCircuitLine(sendRect,receiveRect){
  let circuitLine = new joint.shapes.standard.Link({
    z: 10,
    source:{id:sendRect.id},
    target:{id:receiveRect.id},
    attrs: {
      line: {
        stroke: '#fff',
        strokeWidth: 1
      }
    },
    labels: [{
      attrs: {
        text: { text: '0',  fill: '#fff' },
        rect: { 'fill-opacity': 0 }
      },
      position: {
        offset: receiveRect.position().x > sendRect.position().x ? -15 : 15, //字符位置上下偏移
        distance: 0.5   //字符位置左右偏移 1=100%
      }
    }],
  });
  return circuitLine;
}

export function drawIedCell(iedCode,position,size,showDesc,sideIndex){
  let iedCell = new joint.shapes.standard.Rectangle({
    id: iedCode,
    sideIndex: sideIndex,
    position: position,
    size: size,
    attrs: {
      label: { text: showDesc, fill: '#fff'},
      body: { stroke: '#26a2f8', 'stroke-width': 0.2, fill: '#26a2f8','fill-opacity':'0.2' , width: '100%', height: '100%' }
    }
  });
  return iedCell;
}

export function drawLogicBlockCell(logicItem,vueComponent,position,desc,color){
  return new LogicBlockModel({
    viewType: 'cbView',
    cbData: logicItem,
    vueEntity: vueComponent,
    id: logicItem['F1065_CODE'] + '-rect',
    z: 20,
    position: position,
    attrs: {
      text1: { text: desc },
      body: {
        fill: color
      }
    }
  });
}

export function drawLogicLine(logicItem,cbView,position){
  let f0165Code = logicItem['F1065_CODE']
  return new joint.shapes.standard.Link({
    id: f0165Code + '-line',
    viewType: 'cbLine',
    cbData: logicItem,
    source: {id: cbView.id },
    target: position,
    attrs: {
      line: {
        stroke: '#fff',
        strokeWidth: 1,
        event: 'link:pointerdown',
      }
    }
  });
}


