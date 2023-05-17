import * as joint from 'jointjs';
import { CustomLink } from '@/api/jointjs/defineElement';

export function drawLink(sourceCell,targetCell,topText,bottomText){
  let link = new joint.shapes.standard.Link({
    source: { id: sourceCell.id ,selector:'rect'},
    target: { id: targetCell.id ,selector:'rect'},
    attrs: {
      line: {
        stroke: '#3ca5bd',
        strokeWidth: 1
      },
      '.marker-target': {
        d: 'M 10 0 L 0 5 L 10 10 z',
        // fill: '#fff'
      },
    },
    labels:[{
      attrs: {
        text: {
          text: topText,
          fill: '#4fbad4'
        },
        rect: { 'fill-opacity': 0 }
      },
      position: {
        distance: 0.5,
        offset: -15
      }
    },{
      attrs: {
        text: {
          text: bottomText,
          fill: '#4fbad4'
        },
        rect: { 'fill-opacity': 0 }
      },
      position: {
        distance: 0.5,
        offset:15
      }
    }]
  });
  return link
}

export function drawAppIdLine(startCell, endCell, position, desc,cbData) {
  let statusCode = '';
  if (cbData != undefined && cbData['F1065_STATUSCODE'] != undefined) {
    statusCode = cbData['F1065_STATUSCODE']
  }
  var link13 = new CustomLink({
    source: { id: startCell.id },
    target: { id: endCell.id },
    router: { name: 'oneSide', args: { side: position, padding: 10 } },
    attrs: {
      '.connection': {
        stroke: '#4fbad4',
        fill: 'none'
      }
    },
    labels: [{
      attrs: {
        label: { text: desc , "class":statusCode+'-dialog-cir-'+position}
      }
    }]
  });
  return link13;
}
