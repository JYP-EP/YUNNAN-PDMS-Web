package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gridnt.pdms.domain.Tb1006AnalogDetail;

import java.util.List;
import java.util.Map;

public interface IDeviceStatusService {


    Map<String,List<Map<String,Object>>> getTB1016Points(String f1046Code);

    Map<String, List<Map<String, Object>>> getTB1006Points(String f1046Code);

    Map<String, Object> getBackboardPoints(String f1046Code);

    Page<Map<String, String>> loadHistoryEvent(Page page, Map<String, Object> param);

    Tb1006AnalogDetail loadDetail(String f1006Code);
}
