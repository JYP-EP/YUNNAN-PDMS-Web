package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gridnt.pdms.domain.MonitorFCDA;
import com.gridnt.pdms.domain.SettingFCDA;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MonitorMapper extends BaseMapper<MonitorFCDA> {
	List<MonitorFCDA> getAnalogInfo(@Param("deviceID")String deviceID);
	List<MonitorFCDA> getConditionInfo(@Param("deviceID")String deviceID);

	List<MonitorFCDA> getStatusInfo(@Param("deviceID")String deviceID);
	List<MonitorFCDA> getWarningInfo(@Param("deviceID")String deviceID);
	List<MonitorFCDA> getOtherStatusInfo(@Param("deviceID")String deviceID);
	List<MonitorFCDA> getStripeInfo(@Param("deviceID")String deviceID);

	List<SettingFCDA> getSettingInfo(@Param("deviceID")String deviceID);
	List<SettingFCDA> getParameterInfo(@Param("deviceID")String deviceID);
}
