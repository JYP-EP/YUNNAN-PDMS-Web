package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class G_AnalogXml {
	public static final int ID = 11;
	@JacksonXmlProperty(localName = "QueryTime")
	private G_AnalogQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_AnalogQueryTime{
	@JacksonXmlProperty(isAttribute = true)
	private String start;

	@JacksonXmlProperty(isAttribute = true)
	private String end;

	@JacksonXmlProperty(localName = "IED")
	@JacksonXmlElementWrapper(useWrapping = false)
	private G_AnalogIED[] ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_AnalogIED{
	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(localName = "Warning")
	@JacksonXmlElementWrapper(useWrapping = false)
	private G_AnalogWarning[] warnings;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_AnalogWarning{
	@JacksonXmlProperty(isAttribute = true)
	private String time;

	@JacksonXmlProperty(isAttribute = true, localName = "subType")
	private String subtype;

	@JacksonXmlProperty(localName = "Point")
	private G_AnalogPoint point;

	@JacksonXmlProperty(localName = "Policy")
	private Policy policy;

	@JacksonXmlProperty(localName = "Result")
	private String result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_AnalogPoint{
	@JacksonXmlProperty(isAttribute = true)
	private String path;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(isAttribute = true)
	private String other;

	@JacksonXmlProperty(localName = "Datagrp")
	@JacksonXmlElementWrapper(useWrapping = false)
	private G_DataGroup[] dataGroups;

	@JacksonXmlProperty(localName = "Highlimit")
	private AnalogLimit upperLimit;

	@JacksonXmlProperty(localName = "Lowlimit")
	private AnalogLimit lowerLimit;

	@JacksonXmlProperty(localName = "Alarm")
	private String alarm;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_DataGroup{
	@JacksonXmlProperty(isAttribute = true)
	private String num;

	@JacksonXmlProperty(localName = "Data")
	@JacksonXmlElementWrapper(useWrapping = false)
	private AnalogData[] analogData;
}