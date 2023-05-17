package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CheckReport")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckReportXml {
	public static final int ID = 6;

	@JacksonXmlProperty(localName = "System")
	private CheckSystem system;

	@JacksonXmlProperty(localName = "Ied")
	private CheckIED ied;

	@JacksonXmlProperty(localName = "DiffZone")
	private CheckCategory zone;

	@JacksonXmlProperty(localName = "DiffSetting")
	private CheckCategory setting;

	@JacksonXmlProperty(localName = "DiffSoftplate")
	private CheckCategory softPlate;

	@JacksonXmlProperty(localName = "DiffHardplate")
	private CheckCategory hardPlate;

	@JacksonXmlProperty(localName = "DiffDiscrete")
	private CheckCategory discrete;

	@JacksonXmlProperty(localName = "DiffClock")
	private CheckCategory clock;

	@JacksonXmlProperty(localName = "CommStatus")
	private CheckCategory commStatus;

	@JacksonXmlProperty(localName = "SecCircuit")
	private CheckCategory secCircuit;

	@JacksonXmlProperty(localName = "SoftVersion")
	private CheckCategory softVersion;

	@JacksonXmlProperty(localName = "SelfAlarm")
	private CheckCategory selfAlarm;

	@JacksonXmlProperty(localName = "GpsAlarm")
	private CheckCategory gpsAlarm;

	@JacksonXmlProperty(localName = "Wave")
	private CheckCategory wave;

	@JacksonXmlProperty(localName = "Loop")
	private CheckCategory loop;

	@JacksonXmlProperty(localName = "DiffAnalog")
	private CheckCategory analog;

	@JacksonXmlProperty(localName = "Channel")
	private CheckCategory channel;

	@JacksonXmlProperty(localName = "Switch")
	private CheckCategory onSwitch;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CheckSystem{
	@JacksonXmlProperty(localName = "Substation")
	private String subStation;

	@JacksonXmlProperty(localName = "CheckTime")
	private String time;

	@JacksonXmlProperty(localName = "CheckReason")
	private String reason;

	@JacksonXmlProperty(localName = "DeviceSum")
	private String deviceNumber;

	@JacksonXmlProperty(localName = "CheckDeviceSum")
	private String checkedNumber;

	@JacksonXmlProperty(localName = "AbnormalDeviceSum")
	private String abnormalNumber;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CheckIED{
	@JacksonXmlProperty(localName = "Item")
	@JacksonXmlElementWrapper(useWrapping = false)
	private CheckIEDItem[] items;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CheckIEDItem{
	@JacksonXmlProperty(isAttribute = true, localName = "DeviceId")
	private String deviceId;

	@JacksonXmlProperty(isAttribute = true, localName = "DeviceName")
	private String deviceName;

	@JacksonXmlProperty(isAttribute = true, localName = "IsChecked")
	private String isChecked;

	@JacksonXmlProperty(isAttribute = true, localName = "UnCheckReason")
	private String unCheckReason;

	@JacksonXmlProperty(isAttribute = true, localName = "result")
	private String result;

	@JacksonXmlProperty(localName = "Zone")
	private ItemSummary zone;
	@JacksonXmlProperty(localName = "Setting")
	private ItemSummary setting;
	@JacksonXmlProperty(localName = "SoftPlate")
	private ItemSummary softPlate;
	@JacksonXmlProperty(localName = "HardPlate")
	private ItemSummary hardPlate;
	@JacksonXmlProperty(localName = "Discrete")
	private ItemSummary discrete;
	@JacksonXmlProperty(localName = "Clock")
	private ItemSummary clock;
	@JacksonXmlProperty(localName = "CommStatus")
	private ItemSummary commStatus;
	@JacksonXmlProperty(localName = "SecCircuit")
	private ItemSummary secCircuit;
	@JacksonXmlProperty(localName = "SoftVersion")
	private ItemSummary softVersion;
	@JacksonXmlProperty(localName = "SelfAlarm")
	private ItemSummary selfAlarm;
	@JacksonXmlProperty(localName = "GpsAlarm")
	private ItemSummary gpsAlarm;
	@JacksonXmlProperty(localName = "Wave")
	private ItemSummary wave;
	@JacksonXmlProperty(localName = "Analog")
	private ItemSummary analog;

	@JacksonXmlProperty(localName = "Loop")
	private ItemSummary loop;

	@JacksonXmlProperty(localName = "Channel")
	private ItemSummary channel;

	@JacksonXmlProperty(localName = "Switch")
	private ItemSummary onSwitch;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class ItemSummary{
	@JacksonXmlProperty(isAttribute = true, localName = "IsChecked")
	private String isChecked;

	@JacksonXmlProperty(isAttribute = true, localName = "UnCheckReason")
	private String unCheckReason;

	@JacksonXmlProperty(isAttribute = true, localName = "result")
	private String result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class CheckCategory{
	@JacksonXmlProperty(localName = "Item")
	@JacksonXmlElementWrapper(useWrapping = false)
	private ItemDetail[] itemDetail;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class ItemDetail{
	@JacksonXmlProperty(isAttribute = true, localName = "DeviceId")
	private String deviceId;

	@JacksonXmlProperty(isAttribute = true, localName = "PointId")
	private String pointRef;

	@JacksonXmlProperty(isAttribute = true, localName = "PointName")
	private String pointDesc;

	@JacksonXmlProperty(isAttribute = true, localName = "ValType")
	private String valueType;

	@JacksonXmlProperty(isAttribute = true, localName = "RefVal")
	private String refValue;

	@JacksonXmlProperty(isAttribute = true, localName = "RealVal")
	private String realValue;

	@JacksonXmlProperty(isAttribute = true, localName = "CheckTime")
	private String checkTime;

	@JacksonXmlProperty(isAttribute = true, localName = "IsDifferent")
	private String isDiff;

	@JacksonXmlProperty(isAttribute = true, localName = "ZoneNo")
	private String zoneNO;

	@JacksonXmlProperty(isAttribute = true, localName = "LowerLimit")
	private String lowerLimit;

	@JacksonXmlProperty(isAttribute = true, localName = "UpperLimit")
	private String upperLimit;
}