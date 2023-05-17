package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gridnt.pdms.domain.Tb1042Bay;

import java.util.List;
import java.util.Map;

/**
 * 子站间隔Service接口
 * 
 * @author gridnt
 * @date 2021-10-23
 */
public interface ITb1042BayService extends IService<Tb1042Bay> {

    List<String> getBaysVoltageByStation(String f1041Code);

    List<Map>  loadOpticalFiberListByBay(String bayId);

    List<Map> loadLogicListByBay(String bayId);
}