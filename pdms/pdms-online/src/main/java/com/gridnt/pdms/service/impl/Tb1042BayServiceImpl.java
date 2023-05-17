package com.gridnt.pdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gridnt.pdms.domain.Tb1046Ied;
import com.gridnt.pdms.mapper.Tb1046IedMapper;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gridnt.pdms.mapper.Tb1042BayMapper;
import com.gridnt.pdms.domain.Tb1042Bay;
import com.gridnt.pdms.service.ITb1042BayService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 子站间隔Service业务层处理
 *
 * @author gridnt
 * @date 2021-10-23
 */
@DataSource(value = DataSourceType.SLAVE)
@Service
public class Tb1042BayServiceImpl extends ServiceImpl<Tb1042BayMapper, Tb1042Bay> implements ITb1042BayService {

    @Autowired
    Tb1046IedMapper tb1046IedMapper;

    @Autowired
    Tb1042BayMapper tb1042BayMapper;

    @Override
    public List<String> getBaysVoltageByStation(String f1041Code) {
        return baseMapper.getBaysVoltageByStation(f1041Code);
    }

    @Override
    public List<Map>  loadOpticalFiberListByBay(String bayId) {
        //先查询当前间隔下面的设备
        QueryWrapper<Tb1046Ied> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1042_CODE",bayId);
        queryWrapper.orderByAsc("F1042_CODE","F1046_CODE");
        List<Tb1046Ied> iedList = tb1046IedMapper.selectList(queryWrapper);
        //根据设备信息查询收发的光纤回路
        List<Map> opticalFiberList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(iedList)){
            for(int i = 0;i<iedList.size();i++){
                List<Map> iedOpticalFiberList = new ArrayList<>();
                Tb1046Ied ied = iedList.get(i);
                //根据Ｉed查询本端是发送端光纤回路信息
                List<Map> opticalFiberSendList = tb1046IedMapper.selectSendOpticalFiber(ied.getF1046Code());
                //添加合并单元格行信息
                if(CollectionUtils.isNotEmpty(opticalFiberSendList)){
                    opticalFiberSendList.get(0).put("typerowspan",opticalFiberSendList.size());
                    iedOpticalFiberList.addAll(opticalFiberSendList);
                }
                //根据Ｉed查询本端是接受端光纤回路信息
                List<Map> opticalFiberReceiveList = tb1046IedMapper.selectReceiveOpticalFiber(ied.getF1046Code());
                //添加合并单元格行信息
                if(CollectionUtils.isNotEmpty(opticalFiberReceiveList)){
                    opticalFiberReceiveList.get(0).put("typerowspan",opticalFiberReceiveList.size());
                    iedOpticalFiberList.addAll(opticalFiberReceiveList);
                }
                if(CollectionUtils.isNotEmpty(iedOpticalFiberList)){
                    iedOpticalFiberList.get(0).put("rowspan",iedOpticalFiberList.size());
                    opticalFiberList.addAll(iedOpticalFiberList);
                }
            }
        }
        return opticalFiberList;
    }

    @Override
    public List<Map> loadLogicListByBay(String bayId) {
        //先查询当前间隔下面的设备
        QueryWrapper<Tb1046Ied> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1042_CODE",bayId);
        queryWrapper.orderByAsc("F1042_CODE","F1046_CODE");
        List<Tb1046Ied> iedList = tb1046IedMapper.selectList(queryWrapper);
        //根据设备信息查询收发的逻辑回路
        List<Map> logicList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(iedList)){
            for(int i = 0;i<iedList.size();i++) {
                List<Map> iedLogicList = new ArrayList<>();
                Tb1046Ied ied = iedList.get(i);
                //根据Ｉed查询本端是发送端光纤回路信息
                List<Map> logicSendList = tb1046IedMapper.selectSendLogic(ied.getF1046Code(),"接收");
                //添加合并单元格行信息
                if (CollectionUtils.isNotEmpty(logicSendList)) {
                    logicSendList.get(0).put("typerowspan", logicSendList.size());
                    oppositeRowSpan(logicSendList);
                    iedLogicList.addAll(logicSendList);
                }
                //根据Ｉed查询本端是接受端光纤回路信息
                List<Map> logicReceiveList = tb1046IedMapper.selectSendLogic(ied.getF1046Code(),"发送");
                //添加合并单元格行信息
                if (CollectionUtils.isNotEmpty(logicReceiveList)) {
                    logicReceiveList.get(0).put("typerowspan", logicReceiveList.size());
                    oppositeRowSpan(logicReceiveList);
                    iedLogicList.addAll(logicReceiveList);
                }
                if (CollectionUtils.isNotEmpty(iedLogicList)) {
                    iedLogicList.get(0).put("rowspan", iedLogicList.size());
                    logicList.addAll(iedLogicList);
                }
            }
        }
        return logicList;
    }

    /**
     * 合并对端装置行
     * @param logicSendList
     */
    private void oppositeRowSpan(List<Map> logicSendList){
        String oppositeIedCode = (String) logicSendList.get(0).get("F1046_CODE_OPPOSITE");
        int rowspansize = 0;int rowspanIndex = 0;
        for(int k = 0;k<logicSendList.size();k++){
            Map<String,Object> logicItem = logicSendList.get(k);
            String itemOppositeIedCode =(String) logicItem.get("F1046_CODE_OPPOSITE");
            if(!oppositeIedCode.equals(itemOppositeIedCode)){
                oppositeIedCode = itemOppositeIedCode;
                logicSendList.get(rowspanIndex).put("oppositerowspan",rowspansize);
                rowspanIndex = k;
                rowspansize = 1;
            }else{
                rowspansize++;
            }
        }
        logicSendList.get(rowspanIndex).put("oppositerowspan",rowspansize);
    }

}