package com.gridnt.pdms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.WebUtils;
import com.gridnt.common.web.annotation.MultiRequestBody;
import com.gridnt.pdms.domain.Tb1006AnalogDetail;
import com.gridnt.pdms.service.IDeviceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 设备状态
 *
 */
@RestController
@RequestMapping("/pdms/{version}/devicestatus")
public class DeviceStatusController {

    @Autowired
    IDeviceStatusService deviceStatusService;

    @GetMapping("/platepoint/{f1046Code}")
    public ResponseDTO<Map<String,List<Map<String,Object>>>> getTB1016Points(@PathVariable("f1046Code") String f1046Code){
        Map<String,List<Map<String,Object>>> list =  deviceStatusService.getTB1016Points(f1046Code);
        return WebUtils.createSuccessResponse(list);
    }

    @GetMapping("/backboard/{f1046Code}")
    public ResponseDTO<Map<String,Object>> getBackboardPoints(@PathVariable("f1046Code") String f1046Code){
        Map<String,Object> list =  deviceStatusService.getBackboardPoints(f1046Code);
        return WebUtils.createSuccessResponse(list);
    }

    @GetMapping("/analog/{f1046Code}")
    public ResponseDTO<Map<String,List<Map<String,Object>>>> getTB1006Points(@PathVariable("f1046Code") String f1046Code){
        Map<String,List<Map<String,Object>>> list =  deviceStatusService.getTB1006Points(f1046Code);
        return WebUtils.createSuccessResponse(list);
    }

    /**
     * 查询历史事件
     */
    @PostMapping("historyevent")
    public ResponseDTO<Page<Map<String,String>>> loadHistoryEvent(@MultiRequestBody Page page,@MultiRequestBody Map<String,Object> param){
        Page<Map<String,String>> data = deviceStatusService.loadHistoryEvent(page,param);
        return WebUtils.createSuccessResponse(data);
    }

    /**
     * 查询遥信数据的详细信息
     * @param f1006Code
     * @return
     */
    @GetMapping("detail/{f1006Code}")
    public ResponseDTO<Tb1006AnalogDetail> loadDetail(@PathVariable("f1006Code") String f1006Code){
        Tb1006AnalogDetail viewData =  deviceStatusService.loadDetail(f1006Code);
        return WebUtils.createSuccessResponse(viewData);

    }
}
