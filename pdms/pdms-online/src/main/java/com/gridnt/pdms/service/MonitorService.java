package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gridnt.pdms.domain.MonitorFCDA;
import com.gridnt.pdms.domain.SettingFCDA;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MonitorService extends IService<MonitorFCDA> {
	List<MonitorFCDA> getAnalogInfo(String deviceID);
	List<MonitorFCDA> getConditionInfo(String deviceID);

	List<MonitorFCDA> getStatusInfo(String deviceID);
	List<MonitorFCDA> getWarningInfo(String deviceID);
	List<MonitorFCDA> getOtherStatusInfo(String deviceID);
	List<MonitorFCDA> getStripeInfo(String deviceID);

	List<SettingFCDA> getSettingInfo(String deviceID);
	List<SettingFCDA> getParameterInfo(String deviceID);
}
