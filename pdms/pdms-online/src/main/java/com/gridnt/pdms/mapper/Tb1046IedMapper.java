package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gridnt.pdms.domain.Tb1046Ied;
import com.gridnt.pdms.domain.Tb1046IedListView;
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
public interface Tb1046IedMapper extends BaseMapper<Tb1046Ied> {
    //根据Ｉed查询本端是发送端光纤回路信息
    List<Map> selectSendOpticalFiber(@Param("f1046Id") String f1046Id);

    //根据Ｉed查询本端是接受端光纤回路信息
    List<Map> selectReceiveOpticalFiber(@Param("f1046Id") String f1046Id);

    //根据ied查询本端是发送端的物理回路信息
    List<Map> selectSendLogic(@Param("f1046Id") String f1046Code,@Param("type") String type);
}