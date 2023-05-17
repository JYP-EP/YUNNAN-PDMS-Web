package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gridnt.pdms.domain.Physconnview;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PhysconnviewMapper extends BaseMapper<Physconnview> {
    /**
     * 根据装置查询物理回路图
     * @param f1046Code
     * @return
     */
    List<Physconnview> selectByF1046(@Param("f1046Code") String f1046Code);

    List<Map<String, Object>> loadLogical(@Param("f1053Code") String f1053Code);
}
