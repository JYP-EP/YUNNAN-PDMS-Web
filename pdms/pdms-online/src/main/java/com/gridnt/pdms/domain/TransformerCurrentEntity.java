package com.gridnt.pdms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class TransformerCurrentEntity extends BaseAlertEntity{
	public static final int ID = 5;
}
