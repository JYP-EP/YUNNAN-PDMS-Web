package com.gridnt.pdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridnt.pdms.domain.*;
import com.gridnt.pdms.mapper.CircuitMapper;
import com.gridnt.pdms.mapper.Tb1041SubstationMapper;
import com.gridnt.pdms.mapper.Tb1046IedMapper;
import com.gridnt.pdms.service.ICircuitService;
import com.gridnt.pdms.service.ITb1041SubstationService;
import com.gridnt.pdms.service.ITb1046IedService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 虚回路成图
 *
 * @author gridnt
 * @date 2021-10-23
 */
@DataSource(value = DataSourceType.SLAVE)
@Service
public class CircuitServiceImpl extends ServiceImpl<CircuitMapper, Tb1063Circuitview>  implements ICircuitService {

    private static final String[] types = new String[]{"send","recv"};

    @Autowired
    CircuitMapper circuitMapper;

    @Autowired
    Tb1046IedMapper tb1046IedMapper ;


    @Override
    public List[] getCBDiagram(String f1046Code) {

        List<Map<String,String>> cbList = circuitMapper.loadCBList(f1046Code);
        if(CollectionUtils.isNotEmpty(cbList)){
            Map<String,Map<String,Object>> result =   restoreByIed(f1046Code,cbList);
            return restoreBySide(cbList.size(),result);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> loadPlateAndCircuit(String tb1065Code) {
        List<Map<String,Object>> returnList = new ArrayList<>();
        //先查接收端的压板
        loadCirByParam(returnList,"F1016_CODE_STRAPRECV",types[1],tb1065Code);
        //再查发送端的压板
        loadCirByParam(returnList,"F1016_CODE_STRAPSEND",types[0],tb1065Code);
        //查询没有压板的虚回路
        List<Tb1063Circuitview> cirList =  circuitMapper.loadNoPlateCir(tb1065Code);
        if(CollectionUtils.isNotEmpty(cirList)){
            Map<String,Object> circuitMap = new HashMap<>();
            circuitMap.put("type","noplate");
            circuitMap.put("list",cirList);
            returnList.add(circuitMap);
        }
        return returnList;
    }

    @Override
    public Map<String,Object> loadCircuitAndReality(String tb1065Code) {
        Map<String,Object> result = new HashMap<>();
        //查询虚回路
        result.put("circuit",loadPlateAndCircuit(tb1065Code));
        //查询实回路
        List<Physconnview> realityList = circuitMapper.selectReality(tb1065Code);
        result.put("reality",realityList);
        return result;

    }

    /**
     * 分接收端发送端查询压板及回路
     * @param columnName
     * @param tb1065Code
     */
    private void loadCirByParam(List<Map<String,Object>> returnList,String columnName,String type,String tb1065Code){
    //先查接收端的压板
        List<String> platesList =  circuitMapper.loadPlates(columnName,tb1065Code);
        if(CollectionUtils.isNotEmpty(platesList)){
            for(String plate:platesList){
                Map<String,Object> circuitMap = new HashMap<>();
                //根据压板查询虚回路
                if(StringUtils.isNotEmpty(plate)){
                    List<Tb1063Circuitview> cirList = circuitMapper.loadCircuitByPlate(columnName,plate,tb1065Code);
                    circuitMap.put("type",type);
                    circuitMap.put("plate",plate);
                    circuitMap.put("list",cirList);
                    returnList.add(circuitMap);
                }

            }
        }
    }


    /**
     * 根据Ied分一下类
     * @param cbList
     * @return
     */
    private Map<String,Map<String,Object>> restoreByIed(String tb1046Code,List<Map<String,String>> cbList ){
        Map<String,Map<String,Object>> result = new HashMap<>();
        for(Map<String,String> tb1065:cbList){
            String sendCode = tb1065.get("F1046_CODE_IEDSEND");
            String receiveCode = tb1065.get("F1046_CODE_IEDRECV");
            String otherIed = "";
            String operation = "";
            if(tb1046Code.equals(sendCode)){
                otherIed = receiveCode;
                operation = types[1];
            }else{
                otherIed = sendCode;
                operation = types[0];
            }
            Map<String,Object> sideData = new HashMap<>();
            if(result.containsKey(otherIed)){
                sideData =  result.get(otherIed);
            }else{
                Tb1046Ied ied = tb1046IedMapper.selectOne(new QueryWrapper<Tb1046Ied>().eq("F1046_CODE",otherIed));
                sideData.put("iedInfo",ied);
                result.put(otherIed,sideData);
            }
            List<Map<String,String>> logicList = new ArrayList<>();
            if(!sideData.containsKey(operation)){
                sideData.put(operation,logicList);
            }else{
                logicList = (List<Map<String,String>>)sideData.get(operation);
            }
            logicList.add(tb1065);
        }
        return result;
    }


    /**
     * IED平均分到左右两边
     * @param size
     * @param iedMap
     */
    private List[] restoreBySide(int size,Map<String,Map<String,Object>> iedMap){
        int sideNum = size/2;
        List[] lists  = new List[]{new ArrayList<>(),new ArrayList<>()};
        int sum = 0;
        int iedCount  = 0;
        for(String key: iedMap.keySet()){
            int count = 0;
            for(String key1: iedMap.get(key).keySet()){
                if(!"iedInfo".equals(key1)){
                    count = count+((List)iedMap.get(key).get(key1)).size();
                }
            }
            sum = sum + count;
            if(sum > sideNum){
                if(iedCount == 0){
                    lists[0].add(iedMap.get(key));
                }else{
                    lists[1].add(iedMap.get(key));
                }
            }else{
                lists[0].add(iedMap.get(key));
            }
            iedCount++;
        }
        return lists;
    }
}