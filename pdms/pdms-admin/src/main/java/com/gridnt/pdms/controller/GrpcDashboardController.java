package com.gridnt.pdms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.ResultEnum;
import com.gridnt.common.web.WebUtils;
import com.gridnt.pdms.domain.SubstationMapVo;
import com.gridnt.pdms.domain.Tb1041Substation;
import com.gridnt.pdms.domain.TbCrc;
import com.gridnt.pdms.service.GrpcDashboardClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grpc/{version}/dashboard")
public class GrpcDashboardController {


    @Autowired
    private GrpcDashboardClientService grpcService;





    @GetMapping("/getSubStationStatus")
    public ResponseDTO<Map<String, Object>> getSubStationStatus(@PathVariable("version") String version,
                                                                @RequestParam("f1041Code") String f1041Code) {
        try {
            Map<String, Object> subStationStatus = grpcService.getSubStationStatus(f1041Code);
            return WebUtils.createSuccessResponse(subStationStatus);
        } catch (Exception e) {
            return WebUtils.createResponse(ResultEnum.ERROR, "GRPC服务请求失败");
        }
    }

    @GetMapping("/getAllStationStatusData")
    public ResponseDTO<Map<String, Object>> getSubStationStatus(@PathVariable("version") String version) {
        try {
            Map<String, Object> subStationStatus = grpcService.getAllStationStatusData();
            return WebUtils.createSuccessResponse(subStationStatus);
        } catch (Exception e) {
            return WebUtils.createResponse(ResultEnum.ERROR, "GRPC服务请求失败");
        }
    }

    @GetMapping("/getWarningStatistics")
    public ResponseDTO<Map<String, Object>> getWarningStatistics(@PathVariable("version") String version,
                                                                 @RequestParam("f1041Code") String f1041Code) {
        try {
            Map<String, Object> warningStatistics = grpcService.getWarningStatistics(f1041Code);
            return WebUtils.createSuccessResponse(warningStatistics);
        } catch (Exception e) {
            return WebUtils.createResponse(ResultEnum.ERROR, "GRPC服务请求失败");
        }
    }

    @GetMapping("/getWarningStatisticsList")
    public ResponseDTO<List<Map<String, Object>>> getWarningStatisticsList(@PathVariable("version") String version) {
        try {
            List<Map<String, Object>> warningStatisticsList = grpcService.getWarningStatisticsList();
            return WebUtils.createSuccessResponse(warningStatisticsList);
        }catch (Exception e) {
            return WebUtils.createResponse(ResultEnum.ERROR, "GRPC服务请求失败");
        }
    }

    @GetMapping("getSubstationListForMap")
    public ResponseDTO<List<Tb1041Substation>> getSubstationListForMap(@PathVariable("version") String version) {
        try {
            List<Tb1041Substation> list = grpcService.getSubstationListForMap();
            return WebUtils.createSuccessResponse(list);
        }catch (Exception e) {
            return WebUtils.createResponse(ResultEnum.ERROR, "GRPC服务请求失败");
        }
    }

    /**
     * 请求地图站所状态信息
     * @param version
     * @return
     */
    @GetMapping("getsubstationstatusformap")
    public ResponseDTO<Map<String,SubstationMapVo>> getSubstationStatusForMap(@PathVariable("version") String version) {
        try {
            Map<String,SubstationMapVo> map = grpcService.getSubstationStatusForMap();
            return WebUtils.createSuccessResponse(map);
        }catch (Exception e) {
            return WebUtils.createResponse(ResultEnum.ERROR, "GRPC服务请求失败");
        }
    }

    @GetMapping("getWarningAllCount")
    public ResponseDTO<Integer> getWarningAllCount(@PathVariable("version") String version) {
        try {
            Integer count = grpcService.getWarningAllCount();
            return WebUtils.createSuccessResponse(count);
        }catch (Exception e) {
            return WebUtils.createResponse(ResultEnum.ERROR, "GRPC服务请求失败");
        }
    }

    @PostMapping("dq/status")
    public ResponseDTO<Map<String,SubstationMapVo>> getSubstationStatusByDq(@RequestBody Map<String,Object> params){
        Map<String,SubstationMapVo> map = grpcService.getSubstationStatusByDq(params);
        return WebUtils.createSuccessResponse(map);
    }

}
