package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gridnt.pdms.domain.Tb1042Bay;

import java.util.List;

/**
 * 子站间隔Mapper接口
 * 
 * @author gridnt
 * @date 2021-10-23
 */
public interface Tb1042BayMapper extends BaseMapper<Tb1042Bay> {

    List<String> getBaysVoltageByStation(String f1041Code);

}