<template>
  <div style="display: flex; flex-direction: column; flex: 1; height: 100%; position: relative">
    <div id="svgDivElement" ref="svgDivElementRef"></div>
  </div>
</template>

<script>
import _ from 'lodash';
import $ from 'jquery';
import SvgPanZoom from 'svg-pan-zoom';

export default {
  name: 'SvgWrapper',
  data() {
    return {
      $svgdom: null,
      bindData: {},
      useDomCache: [],
      subscribTsCmd: [],
      gPrecisionCache: {},
      gomCache: {},
      refreshCache: {},
      refreshInterval: null,
      svgName: 'index.svg',
      entityAttributesSubscriptionMap: {},
      chartDialogVisible: false,
      chartHeight: '500px',
      activeIndex: '',
      flag: {
        dialogVisible: false
      },
      entityId: null,
      entityType: null,
      item: null
    };
  },
  props: {
    initSvgName: {
      type: String
    },
    svgBackground: {
      type: String,
      default() {
        return '#ffff';
      }
    }
  },
  watch: {
    initSvgName: {
      immediate: false,
      handler() {
        this.renderSvg();
      }
    }
  },
  mounted() {
    this.renderSvg();
  },
  beforeDestroy() {
    this.clearData();
  },
  methods: {
    subscribe() {
      this.$nextTick(() => {
        let equipmentInfos = [];
        let self = this;
        $('[gridnt-entity-id]').each(function() {
          let gridntEntityId = $(this).attr('gridnt-entity-id');
          let gridntEntityType = $(this).attr('gridnt-entity-type');
          let gridntItem = $(this).attr('gridnt-item');
          let gridntItemType = $(this).attr('gridnt-item-type');
          //后面进行排重
          equipmentInfos.push({
            entityId: gridntEntityId,
            entityType: gridntEntityType,
            point: gridntItem,
            fourRemoteType: gridntItemType
          });
          self.useDomCache.push({
            key: (gridntEntityId + '_' + gridntEntityType + '_' + gridntItem).toLowerCase(),
            dom: $(this)
          });
        });
      });
    },
    renderSvg() {
      if (!this.initSvgName) {
        return;
      }
      this.svgName = this.initSvgName;
      let style = 'width: 100%;height: 100%; flex: 1;display: flex; flex-direction: column';
      this.$refs.svgDivElementRef.style = style + ';background: ' + this.svgBackground;
      this.initSvg();
    },
    handleSelect(index) {
      let fileName = index;
      this.redirectSvg(null, 3, fileName);
      console.info(arguments);
    },
    redirectSvg(event, index, svgName) {
      console.info('redirectSvg :' + svgName);
      window.parent.postMessage({ iotSvg: 'web', name: svgName }, '*');
      if (event) {
        event.preventDefault();
      }
      // this.breadcrumblist.splice(index, this.breadcrumblist.length - index)
      this.svgName = svgName;
      this.initSvg();
    },
    initSvg() {
      let self = this;
      this.clearData();
      if (!this.svgName.endsWith('.svg')) {
        this.svgName += '.svg';
      }
      let svgFilePath = '../../../svg/substation1/' + this.svgName;
      // let svgFilePath = '../../../../public/svg/substation1/' + this.svgName + '?tekek=' + _.random(1, 100000);
      $('#svgDivElement').load(svgFilePath, function(response, repomsestatus) {
        if (repomsestatus === 'success') {
          self.freshSvg();
          self.addEvent();
        }
      });
    },
    addEvent() {
      let self = this;
      $('text').css('cursor', 'pointer');
      //如果有gridntyc属性，那么即可以进行曲线的点击查看
      $('[gridnt-entity-id]').on('click', event => {
        let ele = event.currentTarget;
        //获取href
        this.entityId = ele.getAttribute('gridnt-entity-id');
        this.entityType = ele.getAttribute('gridnt-entity-type');
        this.item = ele.getAttribute('gridnt-item');
        this.flag.dialogVisible = true;
      });
      //如果有gridntref属性，则其点击后即可以进入其它的svg
      $('[gridntref]').on('click', function(event) {
        let ele = event.currentTarget;
        //获取href
        let svgName = ele.getAttribute('gridntref');
        console.log('这是点击事件', svgName);
        self.svgName = svgName;
        self.initSvg();
      });
      //如果有gridntyk，即可以进行遥控
      $('[gridntyk]').on('click', function(event) {
        let ele = event.currentTarget;
        let deviceName = ele.getAttribute('gridntyk') || '开关001';
        //获取href
        self
          .$prompt('请输入密码', '对' + deviceName + '进行遥控', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            inputType: 'password'
          })
          .then(({ value }) => {
            login.checkPassword(value).then(res => {
              //发起遥控
              self.$message({
                type: 'success',
                message: '遥控命令下发成功'
              });
            });
          });
      });
    },
    freshSvg() {
      //修改属性，是svg充满全屏
      this.$svgdom = $('#svgDivElement svg');
      if (!this.$svgdom[0]) {
        return;
      }
      this.$svgdom[0].removeAttribute('preserveAspectRatio');
      this.$svgdom[0].removeAttribute('viewBox');
      this.$svgdom[0].setAttribute('height', '97%');
      this.$svgdom[0].setAttribute('width', '100%');
      //防止 g 标签“点穿”
      let svgStyle = this.$svgdom[0].getAttribute('style');
      if (!svgStyle) {
        svgStyle = 'flex: 1;pointer-events: fill;';
      } else {
        svgStyle = 'flex: 1;pointer-events: fill;' + svgStyle;
      }
      this.$svgdom[0].setAttribute('style', svgStyle);
      //平移限制
      let beforePan = function(oldPan, newPan) {
        let gutterWidth = 100,
          gutterHeight = 100,
          // Computed variables
          sizes = this.getSizes(),
          leftLimit = -((sizes.viewBox.x + sizes.viewBox.width) * sizes.realZoom) + gutterWidth,
          rightLimit = sizes.width - gutterWidth - sizes.viewBox.x * sizes.realZoom,
          topLimit = -((sizes.viewBox.y + sizes.viewBox.height) * sizes.realZoom) + gutterHeight,
          bottomLimit = sizes.height - gutterHeight - sizes.viewBox.y * sizes.realZoom;
        let customPan = {};
        customPan.x = Math.max(leftLimit, Math.min(rightLimit, newPan.x));
        customPan.y = Math.max(topLimit, Math.min(bottomLimit, newPan.y));
        return customPan;
      };
      //绑定 拖动 放缩插件
      SvgPanZoom(this.$svgdom[0], {
        zoomEnabled: true,
        controlIconsEnabled: false,
        fit: 1,
        center: 1,
        customEventsHandler: null,
        dblClickZoomEnabled: false,
        beforePan: beforePan
      });
    },
    refreshSvg(valueList) {
      if (!_.isEmpty(valueList)) {
        console.info('valueList', valueList[0].equipmentNo, valueList);
        _.forEach(valueList, val => {
          let type = val.type;
          // if (type === 'yc' || type === 'ym') {
          let point = val.colName;
          // let equipmentNo = val.equipmentNo
          let value = val.value;
          let unit = val.unit;
          let entityId = val.entityId;
          let entityType = val.entityType;
          let doms = _.filter(this.useDomCache, { key: (entityId + '_' + entityType + '_' + point).toLowerCase() });
          if (doms && !_.isEmpty(doms)) {
            _.forEach(doms, item => {
              // let elementType = item.dom.attr('elementType');
              let id = item.dom.attr('id');
              if (id.indexOf('breaker_rect') != -1) {
                //进行遥信的处理
                if (value == 0) {
                  item.dom.css('fill', 'green');
                } else if (value == 1) {
                  item.dom.css('fill', 'red');
                } else {
                  item.dom.css('fill', 'gray');
                }
              } else {
                let oldText = item.dom.text().trim();
                let semiIndex = oldText.indexOf(':');
                let append = oldText.substr(0);
                let spaceIndex = oldText.indexOf(' ');
                if (spaceIndex == -1) {
                  append = '';
                } else {
                  append = oldText.substr(spaceIndex).trim();
                }
                let prefix = oldText.substr(0, semiIndex).trim();
                let valueString = (value + '').trim();
                item.dom.text(prefix + ':' + valueString + ' ' + append);
              }
            });
          }
          // }
        });
      }
    },
    clearData: function() {
      $('#svgDivElement').html('');
    }
  }
};
</script>

<style scoped>
.app-container {
  padding: 0;
  margin: 0;
}

.svg-main-style {
  width: 100%;
  height: 100%;
  background-color: #000;
}

use[elementtype='transformer'][gridntznhref]:hover {
  cursor: pointer;
}

.el-breadcrumb__inner a,
.el-breadcrumb__inner.is-link {
  font-weight: 700;
  text-decoration: none;
  -webkit-transition: color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  color: #0e95d2;
}

.el-breadcrumb__inner a:hover,
.el-breadcrumb__inner.is-link:hover {
  color: #6fbce2;
  cursor: pointer;
  font-weight: bold !important;
}
</style>
