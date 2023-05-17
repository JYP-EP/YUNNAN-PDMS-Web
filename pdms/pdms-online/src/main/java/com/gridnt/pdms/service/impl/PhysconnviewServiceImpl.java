package com.gridnt.pdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gridnt.pdms.domain.Physconnview;
import com.gridnt.pdms.domain.Tb1046Ied;
import com.gridnt.pdms.mapper.PhysconnviewMapper;
import com.gridnt.pdms.mapper.Tb1046IedMapper;
import com.gridnt.pdms.service.IPhysconnviewService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataSource(value = DataSourceType.SLAVE)
@Service
public class PhysconnviewServiceImpl extends ServiceImpl<PhysconnviewMapper, Physconnview>
        implements IPhysconnviewService {

    private static final String[] types = new String[]{"send","recv"};


    @Autowired
    PhysconnviewMapper physconnviewMapper;

    @Autowired
    Tb1046IedMapper tb1046IedMapper;


    /**
     * 按接受、发送，Ied查询物理回路信息
     * @param f1046Code
     */
    @Override
    public List[] loadGroups( String f1046Code) {
        List<Physconnview> phyList = physconnviewMapper.selectByF1046(f1046Code);
        if(CollectionUtils.isNotEmpty(phyList)){
            Map<String,Map<String,Object>> result =   restoreByIed(f1046Code,phyList);
            return restoreBySide(result);
        }
        return null;
    }

    @Override
    public List<Map<String,Object>> loadLogical(String f1053Code) {
        return physconnviewMapper.loadLogical(f1053Code);
    }


    private Map<String, Map<String,Object>> restoreByIed( String tb1046Code, List<Physconnview> phyList ){
        Map<String,Map<String,Object>> result = new HashMap<>();
        for(Physconnview tb1053:phyList){
            String sendCode = tb1053.getF1046CodeA();
            String receiveCode = tb1053.getF1046CodeB();
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
            List<Physconnview> logicList = new ArrayList<>();
            if(!sideData.containsKey(operation)){
                sideData.put(operation,logicList);
            }else{
                logicList = (List<Physconnview>)sideData.get(operation);
            }
            logicList.add(tb1053);
        }
        return result;
    }

    /**
     * IED平均分到左右两边
     * @param iedMap
     */
    private List[] restoreBySide(Map<String,Map<String,Object>> iedMap){
        int sideNum = iedMap.size()/2 +  (iedMap.size() % 2 == 0? 0:1);
        List[] lists  = new List[]{new ArrayList<>(),new ArrayList<>()};
        int sum = 0;
        for(String key: iedMap.keySet()){
            if(sum < sideNum){
                lists[0].add(iedMap.get(key));
            }else{
                lists[1].add(iedMap.get(key));
            }
            sum++;
        }
        return lists;
    }

}
