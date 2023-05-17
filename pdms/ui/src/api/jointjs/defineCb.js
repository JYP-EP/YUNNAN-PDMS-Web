import * as joint from 'jointjs';
let logicWidth = 120,logicHeight = 40,btnWidth = 15
let LogicBlockModel = joint.dia.Element.define('LogicBlock', {
  collapsed: true,
  attrs: {
    body: {
      refWidth: logicWidth,
      refHeight: logicHeight,
      // strokeWidth: 1,
      // stroke: '#db1e1e',
      // 'fill-opacity': '0.7',
      // rx:10,
      // ry:10,
      fill: '#FCFCFC'
    },
    text: {
      xAlignment: 'middle',
      textVerticalAnchor: 'middle',
      refX: logicWidth/2,
      refY: logicHeight / 2,
      fill: '#fff'
    },
    button: {
      refDx: (logicWidth - btnWidth)/2,
      refDy: logicHeight-(btnWidth/2),
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
      d: 'M 2 7 12 7 M 7 2 7 12'
    }
  }
}, {
  markup: [{
    tagName: 'rect',
    selector: 'body'
  },{
      tagName: 'text',
      selector: 'text1'
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
  toggle: function(shouldCollapse) {
    var buttonD;
    var collapsed = (shouldCollapse === undefined) ? !this.get('collapsed') : shouldCollapse;
    let vueEntity = this.get("vueEntity")
    if (collapsed) {
      buttonD = 'M 2 7 12 7 M 7 2 7 12';
    } else {
      buttonD = 'M 2 7 12 7';

      // this.fitChildren();
    }
    this.attr(['buttonIcon', 'd'], buttonD);
    this.set('collapsed', collapsed);
    vueEntity.handleCBClick(this.get("cbData"),collapsed)
  }

});

export  {LogicBlockModel,logicWidth,logicHeight,btnWidth};




