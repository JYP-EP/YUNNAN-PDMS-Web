package com.gridnt.pdms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gridnt.pdms.constants.StatusQueryType;
import com.gridnt.pdms.domain.Tb1006AnalogDetail;
import com.gridnt.pdms.mapper.DeviceStatusMapper;
import com.gridnt.pdms.service.IDeviceStatusService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@DataSource(value = DataSourceType.SLAVE)
@Service
public class DeviceStatusServiceImpl extends ServiceImpl<DeviceStatusMapper,Map> implements IDeviceStatusService {

    @Autowired
    DeviceStatusMapper deviceStatusMapper;


    @Override
    public Map<String,List<Map<String,Object>>> getTB1016Points(String f1046Code) {
        Map<String,List<Map<String,Object>>> resultMap = new HashMap<>();
        for(StatusQueryType type: StatusQueryType.values()){
            String sql = append1016Sql(type);
            List<Map<String,Object>> list = deviceStatusMapper.getTB1016Points(sql,f1046Code);
            resultMap.put(type.name(),list);
        }
        return resultMap;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getTB1006Points(String f1046Code) {
        Map<String,List<Map<String,Object>>> resultMap = new HashMap<>();
        for(StatusQueryType.AnalogType type: StatusQueryType.AnalogType.values()){
            String sql = append1006Sql(type);
            List<Map<String,Object>> list = deviceStatusMapper.getTB1006Points(sql,f1046Code);
            resultMap.put(type.name(),list);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getBackboardPoints(String f1046Code) {
        Map<String,Object> resultMap = new HashMap<>();
        //查询所有的端口
        List<Map<String, Object>> backboards = deviceStatusMapper.selectBackboard(f1046Code);
        List<String> backboardNames = new ArrayList<>();
        //过滤出背板信息
        if(CollectionUtils.isNotEmpty(backboards)){
            backboardNames = backboards.parallelStream().map(backboard -> (String)backboard.get("F1047_CODE")).distinct().collect(Collectors.toList());
        }
        resultMap.put("boards",backboardNames);
        Map<String,List<Map<String, Object>>> boardMap  = new HashMap<>();
        resultMap.put("points",boardMap);
        for(String f1047Code:backboardNames){
            //将区分每个背板的端口
            List<Map<String, Object>> f1048List = new ArrayList<>();
            boardMap.put(f1047Code,f1048List);
            Iterator<Map<String,Object>> iterator = backboards.iterator();
            while(iterator.hasNext()){
                Map<String,Object> item = iterator.next();
                if(f1047Code.equals(item.get("F1047_CODE"))){
                    f1048List.add(item);
                    iterator.remove();
                }
            }
            //如果同时有Tx和Rx , Tx和Rx是一对，要放到一起

            List<Map<String,Object>> txList = new ArrayList<>();
            List<Map<String,Object>> rxList = new ArrayList<>();
            Iterator<Map<String,Object>> f1048Iterator = f1048List.iterator();
            while(f1048Iterator.hasNext()){
                Map<String,Object> point= f1048Iterator.next();
                if(((String)point.get("F1048_NO")).indexOf("TX") > -1){
                    txList.add(point);
                    f1048Iterator.remove();
                }else if(((String)point.get("F1048_NO")).indexOf("RX") > -1){
                    rxList.add(point);
                    f1048Iterator.remove();
                }
            }
            for(int i = 0;i<txList.size();i++){
                Map<String,Object> txItem = txList.get(i);
                String f1048No = (String) txItem.get("F1048_NO");
                String rxF1048No = f1048No.replace("TX","RX");
                Iterator<Map<String,Object>> rxIterator = rxList.iterator();
                while(rxIterator.hasNext()){
                    Map<String,Object> rxiItem = rxIterator.next();
                    if(rxF1048No.equals(rxiItem.get("F1048_NO"))){
                        txItem.put("rx",rxiItem);
                        rxIterator.remove();
                        break;
                    }
                }
            }
            f1048List.addAll(txList);
            f1048List.addAll(rxList);
        }
        return resultMap;
    }

    @Override
    public Page<Map<String, String>> loadHistoryEvent(Page page, Map<String, Object> param) {
        JSONArray dataValues = (JSONArray)param.get("dateValue");
        String startTm = dataValues.getString(0)+"000000000";
        String endTine = dataValues.getString(1)+"595959999";
        param.put("startTm",startTm);
        param.put("endTm",endTine);
        Page<Map<String, String>> pageData = deviceStatusMapper.loadHistoryEvent(page,param);
//        deviceStatusMapper.selectPage()
        return pageData;
    }

    @Override
    public Tb1006AnalogDetail loadDetail(String f1006Code) {
        Tb1006AnalogDetail dataview  = deviceStatusMapper.loadDetail(f1006Code);
        return dataview;
    }

    private String append1016Sql (StatusQueryType type){
        String sql = "";
        switch (type){
            case PANELLIGHT:
                sql = " A.F1011_NO >=204 and A.F1011_NO<=219 ";
                break;
            case PLPIECE:
                sql = " A.F1011_NO >=600 and A.F1011_NO<=619 ";
                break;
            case FSOFTPLATEN:
                sql = " (A.F1011_NO =  621 or  (A.F1011_NO >=626 and A.F1011_NO<= 699)) ";
                break;
            case ESOFTPLATEN:
                sql = " A.F1011_NO >=622 and A.F1011_NO<= 625 ";
                break;
            case ALARM:
                sql = " A.F1011_NO IN ( 100, 101, 102 ,103 ,151 ,152) ";
                break;
            case ACTION:
                sql = " A.F1011_NO = 400 ";
                break;
            default:;

        }
        return sql;
    }

    private String append1006Sql (StatusQueryType.AnalogType type){
        String sql = "";
        switch (type){
            case TEMP:
                sql = " A.F1011_NO = 901 ";
                break;
            case VOLTAGE:
                sql = " A.F1011_NO >=902 and A.F1011_NO<=  920 ";
                break;
            case LIGHT:
                sql = " A.F1011_NO =  950 ";
                break;
            default:;

        }
        return sql;
    }
}
