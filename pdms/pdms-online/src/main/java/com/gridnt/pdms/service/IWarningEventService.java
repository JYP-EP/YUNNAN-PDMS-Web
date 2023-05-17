package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gridnt.pdms.domain.WarningEventEntity;
import com.gridnt.pdms.domain.WarningEventFilter;
import com.gridnt.pdms.domain.WarningEventStat;

import java.util.List;

public interface IWarningEventService extends IService<WarningEventEntity> {
	Page<WarningEventEntity> getWarningEvent(Page<WarningEventEntity> page, WarningEventFilter filter);
	List<WarningEventStat> getWarningEventStat(WarningEventFilter filter);
}
