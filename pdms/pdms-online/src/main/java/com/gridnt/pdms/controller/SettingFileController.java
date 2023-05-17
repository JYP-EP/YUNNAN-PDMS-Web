package com.gridnt.pdms.controller;


import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.WebUtils;
import com.gridnt.pdms.domain.ConstantValueVo;
import com.gridnt.pdms.domain.SettingDataVo;
import com.gridnt.pdms.service.SettingFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pdms/{version}/settingFile")
public class SettingFileController {

    @Autowired
    SettingFileService settingFileService;

    @PostMapping("getSettingFileData")
    public ResponseDTO<Map<String,ConstantValueVo>> getSettingFileData(@RequestBody  Map<String,String> map) {
        String code = map.get("code");
        Map<String,ConstantValueVo> result  = settingFileService.getSettingFileData(code);
        return WebUtils.createSuccessResponse(result);
    }

}
