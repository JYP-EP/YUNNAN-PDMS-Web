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
public class TunnelAlertXml {
	@JacksonXmlProperty(localName = "QueryTime")
	private TunnelQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TunnelQueryTime {
	@JacksonXmlProperty(localName = "IED")
	private TunnelIED ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TunnelIED {
	@JacksonXmlProperty(isAttribute = true)
	private String name;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	@JacksonXmlProperty(localName = "Warning")
	private TunnelWarning warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TunnelWarning {
	@JacksonXmlProperty(isAttribute = true)
	private String time;

	@JacksonXmlProperty(localName = "Point")
	@JacksonXmlElementWrapper(useWrapping = false)
	private TunnelPoint[] points;

	@JacksonXmlProperty(localName = "Policy")
	private TunnelPolicy policy;

	@JacksonXmlProperty(localName = "Result")
	private TunnelResult result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TunnelPoint {
	@JacksonXmlProperty(isAttribute = true)
	private String path;

	@JacksonXmlProperty(isAttribute = true)
	private String desc;

	private String counter;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TunnelPolicy {
	@JacksonXmlProperty(localName = "Threshold")
	@JacksonXmlElementWrapper(useWrapping = false)
	private TunnelThreshold[] conditions;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TunnelThreshold{
	@JacksonXmlProperty(isAttribute = true)
	private String level;

	@JacksonXmlText
	private String value;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TunnelResult{
	private String counter;
	private String level;
}