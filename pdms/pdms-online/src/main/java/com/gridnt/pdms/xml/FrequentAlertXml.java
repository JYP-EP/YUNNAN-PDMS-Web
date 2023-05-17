package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FrequentAlertXml {
	public static final int ID = 9;
	@JacksonXmlProperty(localName = "QueryTime")
	private QueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class QueryTime {
	@JacksonXmlProperty(localName = "IED")
	private IED ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class IED {
	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(localName = "Warning")
	private Warning warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Warning {
	@JacksonXmlProperty(isAttribute = true)
	private String time;

	@JacksonXmlProperty(localName = "Point")
	@JacksonXmlElementWrapper(useWrapping = false)
	private Point[] points;

	@JacksonXmlProperty(localName = "Policy")
	private Policy policy;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Point {
	@JacksonXmlProperty(isAttribute = true)
	private String path;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	private String value;
	private String time;
	private String quality;
	private String alarm;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Policy {
	@JacksonXmlProperty(localName = "Condition")
	@JacksonXmlElementWrapper(useWrapping = false)
	private Condition[] conditions;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Condition {
	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlText
	private String value;
}
