package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gridnt.pdms.domain.Tb1041Substation;
import com.gridnt.pdms.domain.Tb1063Circuitview;
import com.gridnt.pdms.domain.TbCrc;

import java.util.List;
import java.util.Map;

/**
 * 虚回路成图
 * 
 * @author gridnt
 * @date 2021-10-23
 */
public interface ICircuitService  extends IService<Tb1063Circuitview> {

    List[] getCBDiagram(String f1046Code);

    /**
     * 查询软压板和虚回路
     * @param tb1065Code
     * @return
     */
    List<Map<String,Object>> loadPlateAndCircuit(String tb1065Code);

    /**
     * 查询虚回路 和  虚实结合
     * @param tb1065Code
     * @return
     */

    Map<String,Object> loadCircuitAndReality(String tb1065Code);
}