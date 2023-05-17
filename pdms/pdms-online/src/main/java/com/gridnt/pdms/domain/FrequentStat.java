package com.gridnt.pdms.domain;

import com.gridnt.common.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class FrequentStat extends BaseEntity {
	private String operator;
	private String triggerDate;
	private int frequency;
}
