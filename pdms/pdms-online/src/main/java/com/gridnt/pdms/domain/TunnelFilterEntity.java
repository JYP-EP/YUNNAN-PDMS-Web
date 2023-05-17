package com.gridnt.pdms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TunnelFilterEntity extends BaseAlertFilterEntity{
	private Integer alertLevel;
}
