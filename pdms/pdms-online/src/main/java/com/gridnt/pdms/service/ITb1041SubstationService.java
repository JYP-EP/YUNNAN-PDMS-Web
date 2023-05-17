package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gridnt.pdms.domain.DivisionNode;
import com.gridnt.pdms.domain.Tb1041Substation;
import com.gridnt.pdms.domain.TbCrc;

import java.util.List;

/**
 * 子站Service接口
 * 
 * @author gridnt
 * @date 2021-10-23
 */
public interface ITb1041SubstationService extends IService<Tb1041Substation> {

    Page<TbCrc> getCrcData(Page<TbCrc> page);

    List<String> getDivision();

    List<DivisionNode> getSubstationByDivision(String division);

    List<String> getDQDescList();
}