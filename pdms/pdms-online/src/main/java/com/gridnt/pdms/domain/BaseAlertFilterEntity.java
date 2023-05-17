package com.gridnt.pdms.domain;

import com.gridnt.common.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BaseAlertFilterEntity extends BaseEntity {
	protected String startTime;
	protected String endTime;
	private String operator;
	private String station;
	private String device;

	public String getStartTime() {
		if(startTime == null || startTime.isEmpty() || startTime.length() <=18){
			return startTime;
		}
		ZonedDateTime localDateTime = getLocalDateTime(startTime);
		return localDateTime.getYear() + "-" + localDateTime.getMonthValue() + "-" + localDateTime.getDayOfMonth() + " 00:00:00.000";
	}

	public String getEndTime(){
		if(endTime == null || endTime.isEmpty() || endTime.length() <=18){
			return endTime;
		}
		ZonedDateTime localDateTime = getLocalDateTime(endTime);
		return localDateTime.getYear() + "-" + localDateTime.getMonthValue() + "-" + localDateTime.getDayOfMonth() + " 23:59:59:999";
	}

	private ZonedDateTime getLocalDateTime(String dateTimeString){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
		ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTimeString.substring(0, 19).replace('T', ' ') + " UTC", formatter);
		return zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
	}
}
