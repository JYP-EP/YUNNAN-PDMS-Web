package com.gridnt.pdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gridnt.pdms.domain.TbCrc;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.gridnt.pdms.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gridnt.pdms.mapper.Tb1041SubstationMapper;
import com.gridnt.pdms.service.ITb1041SubstationService;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 子站Service业务层处理
 *
 * @author gridnt
 * @date 2021-10-23
 */
@DataSource(value = DataSourceType.SLAVE)
@Service
public class Tb1041SubstationServiceImpl extends ServiceImpl<Tb1041SubstationMapper, Tb1041Substation> implements ITb1041SubstationService {

    @Autowired
    private  Tb1041SubstationMapper substationMapper;

    @Override
    public Page<TbCrc> getCrcData(Page<TbCrc> page) {
        return substationMapper.getCrcData(page);
    }

    @Override
    public List<String> getDivision(){
        return substationMapper.getDivision();
    }

    @Override
    public List<DivisionNode> getSubstationByDivision(String division){
        QueryWrapper<Tb1041Substation> queryWrapper = Wrappers.query();
        queryWrapper.eq("F1041_DQDESC", division);
        queryWrapper.orderByAsc("F1041_CODE");
        List<Tb1041Substation> substations = this.list(queryWrapper);
        if(substations!=null){
            return  substations.stream().map(DivisionNode::new).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<String> getDQDescList() {
        return baseMapper.getDQDescList();
    }
}