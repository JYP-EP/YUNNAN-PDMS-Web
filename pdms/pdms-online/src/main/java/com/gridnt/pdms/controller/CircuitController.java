package com.gridnt.pdms.controller;

import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.WebUtils;
import com.gridnt.pdms.service.ICircuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 虚回路成图
 */
@RequestMapping("/pdms/{version}/circuit")
@RestController
public class CircuitController {

    @Autowired
    ICircuitService circuitService;


    /**
     * 查询所有关联的控制块和IED
     * @param
     * @return
     */
    @GetMapping("/cb/{f1046Code}")
    public ResponseDTO<List[]> getCBDiagram(@PathVariable("f1046Code")String f1046Code){
        return WebUtils.createSuccessResponse(circuitService.getCBDiagram(f1046Code));
    }

    /**
     *  根据1065Code 查询 软压板、虚回路
     * @param tb1065Code
     * @return
     */
    @GetMapping("/tb1065/group/{tb1065Code}")
    public ResponseDTO<List<Map<String,Object>>> loadPlateAndCircuit(@PathVariable("tb1065Code") String tb1065Code){
        return WebUtils.createSuccessResponse(circuitService.loadPlateAndCircuit(tb1065Code));
    }

    /**
     *  根据1065Code 查询虚回路 和虚实结合
     * @param tb1065Code
     * @return
     */
    @GetMapping("/tb1065/{tb1065Code}")
    public ResponseDTO<Map<String,Object>> loadCircuitAndReality(@PathVariable("tb1065Code") String tb1065Code){
        return WebUtils.createSuccessResponse(circuitService.loadCircuitAndReality(tb1065Code));
    }
}
