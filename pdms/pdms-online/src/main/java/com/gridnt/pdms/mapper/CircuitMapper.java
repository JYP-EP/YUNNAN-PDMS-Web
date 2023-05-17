package com.gridnt.pdms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gridnt.pdms.domain.Physconnview;
import com.gridnt.pdms.domain.Tb1063Circuitview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 保护设备Mapper接口
 * 
 * @author gridnt
 * @date 2021-10-23
 */
@Mapper
public interface CircuitMapper  extends BaseMapper<Tb1063Circuitview> {

    List<Map<String,String>> loadCBList(@Param("f1046Code") String f1046Code);


    /**
     * 查询压板信息
     * @param columnName
     * @param tb1065Code
     * @return
     */
    List<String> loadPlates(@Param("columnName") String columnName, @Param("tb1065Code") String tb1065Code);

    List<Tb1063Circuitview> loadCircuitByPlate(@Param("columnName") String columnName, @Param("plate")String plate, @Param("tb1065Code")String tb1065Code);

    /**
     * 查询没有软压板的虚回路
     * @param tb1065Code
     * @return
     */
    List<Tb1063Circuitview> loadNoPlateCir(@Param("tb1065Code")String tb1065Code);

    /**
     * 虚实结合的虚回路
     * @param tb1065Code
     * @return
     */
    List<Physconnview> selectReality(@Param("tb1065Code")String tb1065Code);
}