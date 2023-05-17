package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalogXml {
	public static final int ID = 11;
	@JacksonXmlProperty(localName = "QueryTime")
	private AnalogQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class AnalogQueryTime{
	@JacksonXmlProperty(isAttribute = true)
	private String start;

	@JacksonXmlProperty(isAttribute = true)
	private String end;

	@JacksonXmlProperty(localName = "IED")
	@JacksonXmlElementWrapper(useWrapping = false)
	private AnalogIED[] ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class AnalogIED{
	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(localName = "Warning")
	@JacksonXmlElementWrapper(useWrapping = false)
	private AnalogWarning[] warnings;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class AnalogWarning{
	@JacksonXmlProperty(isAttribute = true)
	private String time;

	@JacksonXmlProperty(isAttribute = true)
	private String subtype;

	@JacksonXmlProperty(localName = "Point")
	private AnalogPoint point;

	@JacksonXmlProperty(localName = "Policy")
	private Policy policy;

	@JacksonXmlProperty(localName = "Result")
	private String result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class AnalogPoint{
	@JacksonXmlProperty(isAttribute = true)
	private String path;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(isAttribute = true)
	private String other;

	@JacksonXmlProperty(localName = "datagrp")
	@JacksonXmlElementWrapper(useWrapping = false)
	private DataGroup[] dataGroups;

	@JacksonXmlProperty(localName = "highlimit")
	private AnalogLimit upperLimit;

	@JacksonXmlProperty(localName = "lowlimit")
	private AnalogLimit lowerLimit;

	@JacksonXmlProperty(localName = "alarm")
	private String alarm;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DataGroup{
	@JacksonXmlProperty(isAttribute = true)
	private String num;

	@JacksonXmlProperty(localName = "data")
	@JacksonXmlElementWrapper(useWrapping = false)
	private AnalogData[] analogData;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class AnalogData{
	@JacksonXmlProperty(isAttribute = true)
	private String value;

	@JacksonXmlProperty(isAttribute = true)
	private String datetime;

	@JacksonXmlProperty(isAttribute = true)
	private String quality;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class AnalogLimit{
	@JacksonXmlProperty(isAttribute = true)
	private String value;
}