package com.gridnt.pdms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WarningEventFilter extends BaseAlertFilterEntity {
	private final String type = "warning";
	private int startDate;
	private int startClock;
	private int endDate;
	private int endClock;

	public int getStartDate() {
		return parseDate(startTime);
	}

	public int getStartClock(){
		return parseTimeWithMillisecond(startTime);
	}

	public int getEndDate(){
		return parseDate(endTime);
	}

	public int getEndClock(){
		return parseTimeWithMillisecond(endTime);
	}

	// 2022-01-01 00:00:00.000
	private int parseDate(String date) {
		int result = 0;
		if(date != null && date.length() >= 10) {
			result = Integer.parseInt(date.substring(0, 4)) * 10000 + // year
					Integer.parseInt(date.substring(5, 7)) * 100 + // month
					Integer.parseInt(date.substring(8, 10)); // day
		}
		return result;
	}

	// 2022-01-01 00:00:00.000
	private int parseTimeWithMillisecond(String time) {
		int result = 0;
		if(time != null && time.length() >= 23) {
			result = Integer.parseInt(time.substring(11, 13)) * 10000000 + // hour
					Integer.parseInt(time.substring(14, 16)) * 100000 + // minute
					Integer.parseInt(time.substring(17, 19)) * 1000 + // second
					Integer.parseInt(time.substring(20, 23)); // millisecond
		}
		return result;
	}
}
