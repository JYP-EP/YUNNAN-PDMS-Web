package com.gridnt.pdms.domain;

import lombok.Data;

@Data
public class SettingFCDA {
	private String id;

	private String reference;
	private String description;

	private String unit;
	private Float stepSize;
	private Float minVal;
	private Float maxVal;
	private String defaultVal;
}
