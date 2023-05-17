package com.gridnt.pdms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class FrequentAlertEntity extends BaseAlertEntity {
	public static final int ID = 1;
	public static final int AliasID = 9;

	// 告警次数
	private int frequency;

	public void setFrequency(int frequency) {
		this.frequency = frequency;
		this.setStatus(1);
		this.setAlertType(ID);
	}
}
