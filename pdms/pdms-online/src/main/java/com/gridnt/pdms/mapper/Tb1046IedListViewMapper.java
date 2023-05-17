package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gridnt.pdms.domain.SettingFile;
import com.gridnt.pdms.domain.Tb1046Ied;
import com.gridnt.pdms.domain.Tb1046IedListView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Tb1046IedListViewMapper extends BaseMapper<Tb1046IedListView> {
    List<Tb1046IedListView> getTb1046IedListView(String code);
}
