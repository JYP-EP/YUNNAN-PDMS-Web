package com.gridnt.pdms.controller;

import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.WebUtils;
import com.gridnt.pdms.service.ITb1042BayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 根据间隔查询数据信息
 *
 */
@RequestMapping("/pdms/{version}/bay")
@RestController
public class Tb1042BayController {

    @Autowired
    ITb1042BayService tb1042BayService;

    /**
     * 根据Ｂay统计光纤回路
     * @param bayId
     * @return
     */
    @RequestMapping(value = "{bayId}/opticalfiber",method = RequestMethod.GET)
    public ResponseDTO<List<Map>> loadOpticalFiberList(@PathVariable("bayId") String bayId ){
        List<Map>  list = tb1042BayService.loadOpticalFiberListByBay(bayId);
        return WebUtils.createSuccessResponse(list);
    }

    /**
     * 根据Ｂay统计逻辑回路
     * @param bayId
     * @return
     */
    @RequestMapping(value = "{bayId}/logic",method = RequestMethod.GET)
    public ResponseDTO<List<Map>> loadLogicList(@PathVariable("bayId") String bayId ){
        List<Map>  list = tb1042BayService.loadLogicListByBay(bayId);
        return WebUtils.createSuccessResponse(list);
    }


}
