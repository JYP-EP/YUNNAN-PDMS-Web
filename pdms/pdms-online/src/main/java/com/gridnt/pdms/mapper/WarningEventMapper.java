package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gridnt.pdms.domain.WarningEventEntity;
import com.gridnt.pdms.domain.WarningEventFilter;
import com.gridnt.pdms.domain.WarningEventStat;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WarningEventMapper extends BaseMapper<WarningEventEntity> {
	Page<WarningEventEntity> getHistoryEvent(Page<WarningEventEntity> page,
	                                 @Param("queryFilter")WarningEventFilter queryFilter);
	List<WarningEventStat> getWarningEventStat(@Param("queryFilter")WarningEventFilter queryFilter);
}
