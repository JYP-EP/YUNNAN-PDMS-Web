package com.gridnt.pdms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gridnt.pdms.domain.WarningEventEntity;
import com.gridnt.pdms.domain.WarningEventFilter;
import com.gridnt.pdms.domain.WarningEventStat;
import com.gridnt.pdms.mapper.WarningEventMapper;
import com.gridnt.pdms.service.IWarningEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarningEventServiceImpl
	extends ServiceImpl<WarningEventMapper, WarningEventEntity>
	implements IWarningEventService {

	@Autowired
	private WarningEventMapper warningMapper;

	@Override
	public Page<WarningEventEntity> getWarningEvent(Page<WarningEventEntity> page, WarningEventFilter filter) {
		return warningMapper.getHistoryEvent(page, filter);
	}

	@Override
	public List<WarningEventStat> getWarningEventStat(WarningEventFilter filter){
		return warningMapper.getWarningEventStat(filter);
	}
}
