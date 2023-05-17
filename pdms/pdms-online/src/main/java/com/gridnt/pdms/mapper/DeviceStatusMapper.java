package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gridnt.pdms.domain.Tb1006AnalogDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeviceStatusMapper extends BaseMapper<Map> {


    List<Map<String,Object>> getTB1016Points(@Param("appendSql") String sql, @Param("f1046Code") String f1046Code);

    List<Map<String, Object>> getTB1006Points(@Param("appendSql") String sql, @Param("f1046Code") String f1046Code);

    List<Map<String, Object>> selectBackboard(@Param("f1046Code")String f1046Code);

    Page<Map<String, String>> loadHistoryEvent(Page page, @Param("map") Map<String, Object> param);

    Tb1006AnalogDetail loadDetail(@Param("f1006Code") String f1006Code);
}
