package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gridnt.pdms.domain.Tb1041Substation;
import com.gridnt.pdms.domain.TbCrc;

import java.util.List;

/**
 * 子站Mapper接口
 *
 * @author gridnt
 * @date 2021-10-23
 */
public interface Tb1041SubstationMapper extends BaseMapper<Tb1041Substation> {

    @SqlParser(filter=true)
    Page<TbCrc> getCrcData(Page<TbCrc> page);

    List<String> getDivision();

    List<String> getDQDescList();
}