package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gridnt.pdms.domain.ConstantValueVo;
import com.gridnt.pdms.domain.SettingDataVo;
import com.gridnt.pdms.domain.SettingFile;

import java.util.Map;

public interface SettingFileService  extends IService<SettingFile> {

    Map<String,ConstantValueVo> getSettingFileData(String code);

}
