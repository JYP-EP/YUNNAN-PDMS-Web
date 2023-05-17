import * as joint from 'jointjs';

export let portWidth = 8;
const leftPortView = {
  position: {
    name: 'left', // Layout name
    args: {} // Arguments for port layout function, properties depend on type of layout
  },
  label: {
    position: {
      name: 'right',
      args: { y: 0 }
    },
    markup: [{
      tagName: 'text',
      selector: 'label'
    }]
  },
  attrs: {
    body: {
      magnet: 'passive',
      width: portWidth,
      height: portWidth,
      stroke: '#fff',
      fill: 'transparent',
      'stoke-width': 3,
      x: -(portWidth / 2),
      y: -(portWidth / 2)
    },
    label: { text: 'in1', fill: '#fff' }
  },
  markup: [{
    tagName: 'rect',
    selector: 'body'
  }]
};
const rightPortView = JSON.parse(JSON.stringify(leftPortView));
rightPortView.position.name = 'right';
rightPortView.label.position = 'left';
export function drawIedCell(iedCode, position, size, showDesc, sideIndex) {
  let iedCell = new joint.shapes.standard.Rectangle({
    id: iedCode,
    sideIndex: sideIndex,
    position: position,
    size: size,
    attrs: {
      label: { text: showDesc, fill: '#fff' },
      body: {
        stroke: '#26a2f8',
        'stroke-width': 1,
        fill: '#26a2f8',
        'fill-opacity': '0.2',
        width: '100%',
        height: '100%'
      }
    },
    ports: {
      groups: {
        'groupLeft': leftPortView,
        'groupRight': rightPortView
      }
    }
  });
  return iedCell;
}
let btnWidth = 15
export function drawLink(pageWidth, sourceCell, targetCell, topText, bottomText) {
  let link = new joint.shapes.standard.Link({
    collapsed: false,
    viewType:'physicLine',
    source: { id: sourceCell.id ,selector:'rect'},
    target: { id: targetCell.id ,selector:'rect'},
    attrs: {
      line: {
        stroke: '#3ca5bd',
        strokeWidth: 1
      },
      button: {
        ref: 'line',
        refX: '0.5',
        refDy: -5,
        cursor: 'pointer',
        event: 'element:button:pointerdown',
        title: 'Collapse / Expand'
      },
      buttonBorder: {
        width: btnWidth,
        height: btnWidth,
        fill: '#006fbe',
        // fillOpacity: 0.2,
        stroke: '#FFFFFF',
        strokeWidth: 0
      },
      buttonIcon: {
        fill: 'none',
        stroke: '#fff',
        strokeWidth: 2,
        d: 'M 2 7 12 7'
      }
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
        distance: 0.25,
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
        distance: 0.75,
        offset:-15
      }
    }],
    markup: [
      {
        tagName: 'path',
        selector: 'line',
      },{
      tagName: 'g',
      selector: 'button',
      children: [{
        tagName: 'rect',
        selector: 'buttonBorder'
      }, {
        tagName: 'path',
        selector: 'buttonIcon'
      }]
    }],
  });
  return link
}


export function drawLogicLink(logicData,sourceCell,targetCell,topText){
  let link = new joint.shapes.standard.Link({
    viewType: 'logicLine',
    viewData: logicData,
    source: { id: sourceCell.id ,selector:'rect'},
    target: { id: targetCell.id ,selector:'rect'},
    attrs: {
      line: {
        stroke: '#3ca5bd',
        strokeWidth: 1
      }
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
    }]
  });
  return link
}



