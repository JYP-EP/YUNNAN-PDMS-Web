package com.gridnt.pdms.domain;

import lombok.Data;

@Data
public class MonitorFCDA {
	private String id;

	private String reference;
	private String description;
	private String deviceDesc;
	private String fcdaType;
	private String lowLimit;
	private String highLimit;
}
