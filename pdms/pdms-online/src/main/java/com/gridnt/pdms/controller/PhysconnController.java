package com.gridnt.pdms.controller;

import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.WebUtils;
import com.gridnt.pdms.service.IPhysconnviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 物理回路
 */
@RequestMapping("/pdms/{version}/physconn")
@RestController
public class PhysconnController {

    @Autowired
    IPhysconnviewService physconnviewService;

    /**
     * 按接收端，发送端，ied查询物理回路图
     */
    @PostMapping("group")
    public ResponseDTO<List[]> loadGroups(@RequestBody Map<String,String> param){
        String f1046Code = param.get("f1046Code");
        List[] result = physconnviewService.loadGroups(f1046Code);
        return WebUtils.createSuccessResponse(result);

    }

    /**
     * 根据物理回路图查询关联的逻辑链路图
     */
    @GetMapping("{f1053Code}/logical")
    public ResponseDTO<List<Map<String,Object>>> loadLogical(@PathVariable("f1053Code") String f1053Code){
        List<Map<String,Object>> result= physconnviewService.loadLogical(f1053Code);
        return WebUtils.createSuccessResponse(result);

    }

}
