package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunicationRatioXml {
	public static final int ID = 8;

	@JacksonXmlProperty(localName = "QueryTime")
	private RatioQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class RatioQueryTime{
	@JacksonXmlProperty(isAttribute = true)
	private String start;

	@JacksonXmlProperty(isAttribute = true)
	private String end;

	@JacksonXmlProperty(localName = "IED")
	@JacksonXmlElementWrapper(useWrapping = false)
	private RatioIED[] ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class RatioIED {
	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(localName = "Point")
	@JacksonXmlElementWrapper(useWrapping = false)
	private RatioPoint[] points;

	@JacksonXmlProperty(localName = "Rate")
	private String rate;

	@JacksonXmlProperty(localName = "Result")
	private String result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class RatioPoint {
	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	private String value;
	private String duration;
}


