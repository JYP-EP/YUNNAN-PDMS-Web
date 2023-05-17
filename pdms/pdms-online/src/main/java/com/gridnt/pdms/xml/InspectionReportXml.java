package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "InspectionReport")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InspectionReportXml {
	public static final int ID = 7;
	@JacksonXmlProperty(localName = "System")
	private InspectionSystem system;

	@JacksonXmlProperty(localName = "Level")
	private InspectionLevel level;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class InspectionSystem{
	@JacksonXmlProperty(localName = "Substation")
	private String station;

	@JacksonXmlProperty(localName = "StartTime")
	private String startTime;

	@JacksonXmlProperty(localName = "EndTime")
	private String endTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class InspectionLevel{
	@JacksonXmlProperty(isAttribute = true, localName = "Name")
	private String name;

	@JacksonXmlProperty(localName = "Bay")
	@JacksonXmlElementWrapper(useWrapping = false)
	private InspectionBay[] bay;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class InspectionBay{
	@JacksonXmlProperty(isAttribute = true, localName = "Name")
	private String name;

	@JacksonXmlProperty(localName = "Ied")
	@JacksonXmlElementWrapper(useWrapping = false)
	private InspectionIED[] devices;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class InspectionIED{
	@JacksonXmlProperty(isAttribute = true, localName = "DeviceId")
	private String id;

	@JacksonXmlProperty(isAttribute = true, localName = "DeviceName")
	private String name;

	@JacksonXmlProperty(isAttribute = true, localName = "NextTime")
	private String nextTime;

	@JacksonXmlProperty(localName = "Function")
	@JacksonXmlElementWrapper(useWrapping = false)
	private InspectionFunction[] functions;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class InspectionFunction{
	@JacksonXmlProperty(isAttribute = true, localName = "FunctionId")
	private String id;

	@JacksonXmlProperty(isAttribute = true, localName = "FunctionName")
	private String name;

	@JacksonXmlProperty(isAttribute = true, localName = "NextTime")
	private String nextTime;

	@JacksonXmlProperty(isAttribute = true, localName = "Period")
	private String period;

	@JacksonXmlProperty(isAttribute = true, localName = "Result")
	private String result;

	@JacksonXmlProperty(localName = "Item")
	@JacksonXmlElementWrapper(useWrapping = false)
	private InspectionItem[] item;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class InspectionItem{
	@JacksonXmlProperty(isAttribute = true, localName = "SubFuncId")
	private String id;

	@JacksonXmlProperty(isAttribute = true, localName = "PointId")
	private String reference;

	@JacksonXmlProperty(isAttribute = true, localName = "PointName")
	private String name;

	@JacksonXmlProperty(isAttribute = true, localName = "PointAttrId1")
	private String attribute1;

	@JacksonXmlProperty(isAttribute = true, localName = "PointAttrId2")
	private String attribute2;

	@JacksonXmlProperty(isAttribute = true, localName = "ValType")
	private String valueType;

	@JacksonXmlProperty(isAttribute = true, localName = "RefVal")
	private String refValue;

	@JacksonXmlProperty(isAttribute = true, localName = "RealVal")
	private String realValue;

	// 基准值
	@JacksonXmlProperty(isAttribute = true, localName = "BenVal")
	private String benVal;

	@JacksonXmlProperty(isAttribute = true, localName = "Result")
	private String result;

	@JacksonXmlProperty(isAttribute = true, localName = "LowerLimit")
	private String lowerLimit;

	@JacksonXmlProperty(isAttribute = true, localName = "UpperLimit")
	private String upperLimit;

	@JacksonXmlProperty(isAttribute = true, localName = "CheckTime")
	private String checkTime;

	@JacksonXmlProperty(isAttribute = true, localName = "NextTime")
	private String nextTime;

	@JacksonXmlProperty(isAttribute = true, localName = "Quantum")
	private String quantum;

	@JacksonXmlProperty(isAttribute = true, localName = "Ratio")
	private String ratio;
}