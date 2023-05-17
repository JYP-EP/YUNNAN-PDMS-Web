package com.gridnt.pdms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gridnt.pdms.domain.MonitorFCDA;
import com.gridnt.pdms.domain.SettingFCDA;
import com.gridnt.pdms.mapper.MonitorMapper;
import com.gridnt.pdms.service.MonitorService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DataSource(value = DataSourceType.SLAVE)
public class MonitorServiceImpl extends ServiceImpl<MonitorMapper, MonitorFCDA> implements MonitorService {
	@Resource
	private MonitorMapper monitorMapper;

	@Override
	public List<MonitorFCDA> getAnalogInfo(String deviceID) {
		return monitorMapper.getAnalogInfo(deviceID);
	}

	@Override
	public List<MonitorFCDA> getConditionInfo(String deviceID){
		return monitorMapper.getConditionInfo(deviceID);
	}

	@Override
	public List<MonitorFCDA> getStatusInfo(String deviceID){
		return monitorMapper.getStatusInfo(deviceID);
	}
	@Override
	public List<MonitorFCDA> getWarningInfo(String deviceID){
		return monitorMapper.getWarningInfo(deviceID);
	}
	@Override
	public List<MonitorFCDA> getOtherStatusInfo(String deviceID){
		return monitorMapper.getOtherStatusInfo(deviceID);
	}
	@Override
	public List<MonitorFCDA> getStripeInfo(String deviceID){
		return monitorMapper.getStripeInfo(deviceID);
	}

	@Override
	public List<SettingFCDA> getSettingInfo(String deviceID){
		return monitorMapper.getSettingInfo(deviceID);
	}
	@Override
	public List<SettingFCDA> getParameterInfo(String deviceID){
		return monitorMapper.getParameterInfo(deviceID);
	}
}
