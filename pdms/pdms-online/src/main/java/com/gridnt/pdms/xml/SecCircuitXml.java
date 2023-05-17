package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecCircuitXml {
	public static final int ID = 10;

	@JacksonXmlProperty(localName = "QueryTime")
	private SecQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecQueryTime{
	@JacksonXmlProperty(isAttribute = true)
	private String start;

	@JacksonXmlProperty(isAttribute = true)
	private String end;

	@JacksonXmlProperty(localName = "IED")
	@JacksonXmlElementWrapper(useWrapping = false)
	private SecIED[] ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecIED {
	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(localName = "Warning")
	@JacksonXmlElementWrapper(useWrapping = false)
	private SecWarning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecWarning {
	@JacksonXmlProperty(isAttribute = true)
	private String time;

	@JacksonXmlProperty(isAttribute = true)
	private String subtype;

	@JacksonXmlProperty(localName = "Point")
	@JacksonXmlElementWrapper(useWrapping = false)
	private SecPoint[] points;

	@JacksonXmlProperty(localName = "FaultFile")
	private FaultFile faultFile;

	@JacksonXmlProperty(localName = "Result")
	private String result;

	@JacksonXmlProperty(localName = "Suggestion")
	private String suggestion;

	@JacksonXmlProperty(localName = "Board")
	@JacksonXmlElementWrapper(useWrapping = false)
	private Connector[] board;

	@JacksonXmlProperty(localName = "Port")
	@JacksonXmlElementWrapper(useWrapping = false)
	private Connector[] port;

	@JacksonXmlProperty(localName = "Cable")
	@JacksonXmlElementWrapper(useWrapping = false)
	private Connector[] cable;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecPoint {
	@JacksonXmlProperty(isAttribute = true)
	private String path;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(isAttribute = true)
	private String other;

	private String value;
	private String time;
	private String quality;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class FaultFile{
	@JacksonXmlProperty(localName = "COMTRADE")
	private String comTrade;

	@JacksonXmlProperty(localName = "MIDDLE")
	private String middle;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Connector{
	@JacksonXmlProperty(isAttribute = true, localName = "Slot")
	private String slot;

	@JacksonXmlProperty(isAttribute = true, localName = "Id")
	private String id;

	@JacksonXmlProperty(isAttribute = true, localName = "Priority")
	private String priority;
}