import * as joint from 'jointjs';
import { btnWidth } from '@/api/jointjs/defineCb';

let plateWidth = 60;
let plateModel = joint.dia.Element.define('SoftPlate', {
  size: { width: plateWidth },
  attrs: {
    body: {
      refWidth: '1',
      refHeight: '1',
      fill: '#050914',
      stroke: '#5f77ff'
    },
    circle1: {
      fill: 'transparent',
      stroke: '#fff',
      refCx: '0.2',
      refCy: '0.5',
      r: 'calc(0.1 * w)'
    },
    circle2: {
      fill: 'transparent',
      stroke: '#fff',
      refCx: '0.8',
      refCy: '0.5',
      r: 'calc(0.1 * w)'
    },
    line: {
      'stroke-dasharray': '2 2',
      stroke: '#fff',
      x1: 'calc(0.2 * w)',
      x2: 'calc(0.8 * w)',
      y1: 'calc(0.5 * h - calc(0.1 * w))',
      y2: 'calc(0.5 * h - calc(0.1 * w))'
    },
    line2: {
      'stroke-dasharray': '2 2',
      stroke: '#fff',
      x1: 'calc(0.2 * w)',
      x2: 'calc(0.8 * w)',
      y1: 'calc(0.5 * h + calc(0.1 * w))',
      y2: 'calc(0.5 * h + calc(0.1 * w))'
    }
  }
}, {
  markup: [{
    tagName: 'rect',
    selector: 'body'
  }, {
    tagName: 'circle',
    selector: 'circle1'
  }
    , {
      tagName: 'circle',
      selector: 'circle2'
    }, {
      tagName: 'line',
      selector: 'line1'

    }
    , {
      tagName: 'line',
      selector: 'line2'

    }
  ]
});
let circleOffset = 15
let plateOpenModel = joint.dia.Element.define('SoftOpenPlate', {
  size: { width: plateWidth },
  attrs: {
    body: {
      refWidth: '1',
      refHeight: '1',
      fill: '#050914',
      stroke: '#5f77ff'
    },
    circle1: {
      fill: 'transparent',
      stroke: '#fff',
      refCx: '0.2',
      cy: 'calc( 0.5 * h + '+circleOffset+')',
      r: 'calc(0.1 * w)'
    },
    circle2: {
      fill: 'transparent',
      stroke: '#fff',
      refCx: '0.8',
      cy: 'calc( 0.5 * h + '+circleOffset+')',
      r: 'calc(0.1 * w)'
    },
    circle3: {
      fill: 'transparent',
      stroke: '#fff',
      refCx: '0.2',
      cy: 'calc( 0.5 * h - '+circleOffset+')',
      r: 'calc(0.1 * w)'
    },
    line: {
      'stroke-dasharray': '2 2',
      stroke: '#fff',
      x1: 'calc(0.2 * w)',
      x2: 'calc(0.8 * w)',
      y1: 'calc(0.5 * h  - calc(0.1 * w + '+circleOffset+'))',
      y2: 'calc(0.5 * h - calc(0.1 * w -'+circleOffset+'))'
    },
    line2: {
      'stroke-dasharray': '2 2',
      stroke: '#fff',
      x1: 'calc(0.2 * w)',
      x2: 'calc(0.8 * w)',
      y1: 'calc(0.5 * h + calc(0.1 * w - '+circleOffset+'))',
      y2: 'calc(0.5 * h + calc(0.1 * w + '+circleOffset+'))'
    },
  }
}, {
  markup: [{
    tagName: 'rect',
    selector: 'body'
  }, {
    tagName: 'circle',
    selector: 'circle1'
  }, {
    tagName: 'circle',
    selector: 'circle2'
  },{
    tagName: 'circle',
    selector: 'circle3'
  }, {
    tagName: 'line',
    selector: 'line1'

  }, {
    tagName: 'line',
    selector: 'line2'
  }
  ]
});

let cirMarginTop = 40;
let CirIed = joint.dia.Element.define('CirIed', {
  attrs: {
    body: {
      refWidth: '1',
      refHeight: '1',
      stroke: '#3ca5bd',
      'stroke-width': 1,
      fill: '#003363'
    },
    text1: {
      xAlignment: 'middle',
      textVerticalAnchor: 'top',
      fill: ' #4fbad4',
      refX: '50%',
      refY: cirMarginTop
    },
    text2: {
      xAlignment: 'middle',
      textVerticalAnchor: 'top',
      fill: ' #4fbad4',
      refX: '50%',
      refY: cirMarginTop + 30
    }
  }
}, {
  markup: [{
    tagName: 'rect',
    selector: 'body'
  }, {
    tagName: 'text',
    selector: 'text1'
  }, {
    tagName: 'text',
    selector: 'text2'
  }
  ]
});


var CustomLink = joint.dia.Link.define('CustomLink', {
  defaultLabel: {
    markup: [
      {
        tagName: 'text',
        selector: 'label'
      }
    ],
    attrs: {
      label: {
        text: 'ssss', // default label text
        fill: '#4fbad4', // default text color
        fontSize: 14,
        textAnchor: 'middle',
        yAlignment: 'middle',
        pointerEvents: 'none'
      }
    },
    position: {
      distance: 0.5, // place label at midpoint by default
      offset: {
        y: -20 // offset label by 20px upwards by default
      },
      args: {
        absoluteOffset: true // keep offset absolute when moving by default
      }
    }
  }
});
/**
 * 光芯
 * @type {number}
 */
var CableModel = joint.dia.Element.define('Cable', {
  size: { height: 20 },
  attrs: {
    body: {
      refWidth: '1',
      refHeight: '1',
      stroke: '#3ca5bd',
      'stroke-width': 1,
      fill: '#003363'
    },
    text1: {
      xAlignment: 'middle',
      textVerticalAnchor: 'top',
      refX: '50%',
      fill: ' #4fbad4'
    },
    text2: {
      xAlignment: 'left',
      textVerticalAnchor: 'top',
      fill: ' #4fbad4',
      refY: '25',
      text: '光强：--'
    }

  }
}, {
  markup: [{
    tagName: 'rect',
    selector: 'body'
  }, {
    tagName: 'text',
    selector: 'text1'
  }, {
    tagName: 'text',
    selector: 'text2'
  }]
});
export { plateModel, plateOpenModel,plateWidth, CirIed, cirMarginTop, CustomLink, CableModel };
