package com.gridnt.pdms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.WebUtils;
import com.gridnt.common.web.annotation.MultiRequestBody;
import com.gridnt.pdms.domain.WarningEventEntity;
import com.gridnt.pdms.domain.WarningEventFilter;
import com.gridnt.pdms.domain.WarningEventStat;
import com.gridnt.pdms.service.IWarningEventService;
import com.ruoyi.common.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pdms/{version}/statistics")
public class WarningStatisticsController extends BaseController {
	@Autowired
	private IWarningEventService warningEventService;

	@RequestMapping("warning")
	public ResponseDTO<IPage<WarningEventEntity>> getWarningEvent(@PathVariable("version") String version,
	                                                              @MultiRequestBody("page") Page<WarningEventEntity> page,
	                                                              @MultiRequestBody("entity")WarningEventFilter filter) {
		return WebUtils.createSuccessResponse(warningEventService.getWarningEvent(page, filter));
	}

	@RequestMapping("warning/stat")
	public ResponseDTO<List<WarningEventStat>> getWarningStat(@PathVariable("version") String version,
	                                                          @MultiRequestBody("entity")WarningEventFilter filter) {
		return WebUtils.createSuccessResponse(warningEventService.getWarningEventStat(filter));
	}
}
