package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompareXml {

	public static final int ID = 13;
	@JacksonXmlProperty(localName = "QueryTime")
	private CompareQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CompareQueryTime{
	@JacksonXmlProperty(isAttribute = true)
	private String start;

	@JacksonXmlProperty(isAttribute = true)
	private String end;

	@JacksonXmlProperty(localName = "IED")
	@JacksonXmlElementWrapper(useWrapping = false)
	private CompareIED[] ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CompareIED{
	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(localName = "Warning")
	@JacksonXmlElementWrapper(useWrapping = false)
	private CompareWarning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CompareWarning{
	@JacksonXmlProperty(isAttribute = true)
	private String time;

	@JacksonXmlProperty(isAttribute = true)
	private String type;

	@JacksonXmlProperty(localName = "Point")
	@JacksonXmlElementWrapper(useWrapping = false)
	private ComparePoint[] points;

	@JacksonXmlProperty(localName = "CompareTemplate")
	private CompareTemplate compareTemplate;

	@JacksonXmlProperty(localName = "Result")
	private String result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class ComparePoint{
	@JacksonXmlProperty(isAttribute = true)
	private String path;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(isAttribute = true)
	private String other;

	private String value;
	private String time;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CompareTemplate{
	@JacksonXmlProperty(localName = "Point")
	@JacksonXmlElementWrapper(useWrapping = false)
	private ComparePoint[] points;

	private String value;
	private String time;
}
