package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gridnt.pdms.domain.Physconnview;
import com.gridnt.pdms.domain.SettingFile;

import java.util.List;
import java.util.Map;

public interface SettingFileMapper  extends BaseMapper<SettingFile> {
    List<SettingFile> getSettingFileDataByCode(String code);
}
