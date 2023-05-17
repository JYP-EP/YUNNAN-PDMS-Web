package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoubleAnalogXml {
	public static final int ID = 12;
	@JacksonXmlProperty(localName = "QueryTime")
	private DoubleQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DoubleQueryTime{
	@JacksonXmlProperty(isAttribute = true)
	private String start;

	@JacksonXmlProperty(isAttribute = true)
	private String end;

	@JacksonXmlProperty(localName = "Warning")
	@JacksonXmlElementWrapper(useWrapping = false)
	private DoubleWarning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DoubleWarning{
	@JacksonXmlProperty(isAttribute = true)
	private String time;

	@JacksonXmlProperty(isAttribute = true)
	private String type;

	@JacksonXmlProperty(localName = "Pair")
	@JacksonXmlElementWrapper(useWrapping = false)
	private DoublePair[] pairs;

	@JacksonXmlProperty(localName = "Policy")
	private Policy policy;

	@JacksonXmlProperty(localName = "Result")
	private String result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DoublePair{
	@JacksonXmlProperty(localName = "Point")
	@JacksonXmlElementWrapper(useWrapping = false)
	private DoublePoint[] points;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DoublePoint{
	@JacksonXmlProperty(isAttribute = true)
	private String path;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(isAttribute = true)
	private String other;

	private String value;
	private String highlimit;
	private String lowlimit;
	private String time;
	private String quality;
	private String ied;
}

