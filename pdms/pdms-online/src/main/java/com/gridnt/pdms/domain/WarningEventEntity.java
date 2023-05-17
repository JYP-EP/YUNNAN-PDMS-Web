package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gridnt.common.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("TB_HIS_EVENT")
public class WarningEventEntity extends BaseEntity {
	private String substationName;
	private String operator;
	private String bayName;
	private String deviceName;
	private String targetName;
	private String description;

	private int date;
	private int time;

	private String eventTime;

	public String getEventTime() {
		String dateString = String.valueOf(date);
		String timeString = String.valueOf(time);
		eventTime = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6, 8);
		eventTime += " ";
		StringBuilder fullTimeString = new StringBuilder();
		fullTimeString.append("0".repeat(Math.max(0, (9 - timeString.length()))));
		fullTimeString.append(timeString);
		eventTime += fullTimeString.substring(0, 2) + ":" + fullTimeString.substring(2, 4) + ":"
					+ fullTimeString.substring(4, 6) + "." + fullTimeString.substring(6, 8);
		return eventTime;
	}
}
