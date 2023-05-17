package com.gridnt.pdms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class BlockingAlertEntity extends BaseAlertEntity{
	public static final int ID = 3;
	// 告警级别(闭锁等级)
	private int alertLevel;

	public void setAlertLevel(int alertLevel) {
		this.alertLevel = alertLevel;
		this.setAlertType(ID);
		this.setStatus(1);
	}
}
