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
public class IntelAlertEntity extends BaseEntity{
	private String operator;
	private String substationName;
	private String iedName;
	private String triggerTime;
	private int alertType;
	private int source;
	private String fileId;
	private int status;
}
