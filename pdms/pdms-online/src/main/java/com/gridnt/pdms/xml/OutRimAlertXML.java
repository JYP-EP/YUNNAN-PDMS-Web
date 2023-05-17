package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

//区外分析
@Data
@JacksonXmlRootElement(localName = "outAreaFault")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutRimAlertXML {
    public static final int ID = 4;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(localName = "deviceInformation")
    private DeviceInformation deviceInformation;

    @JacksonXmlProperty(localName = "ChannelValue")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ChannelValue> channelValue;

    @JacksonXmlProperty(localName = "DigitalInfo")
    private DigitalInfo digitalInfo;

    @JacksonXmlProperty(localName = "settingInfo")
    private SettingInfo settingInfo;

    @JacksonXmlProperty(localName = "result")
    private outRimResult resultInfo;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DeviceInformation {
    @JacksonXmlProperty(isAttribute = true)
    private String stationDesc;

    @JacksonXmlProperty(isAttribute = true)
    private String volDesc;
    @JacksonXmlProperty(isAttribute = true)
    private String iedName;

    @JacksonXmlProperty(isAttribute = true)
    private String iedDesc;
    @JacksonXmlProperty(isAttribute = true)
    private String equipmentName;

    @JacksonXmlProperty(isAttribute = true)
    private String recordingDate;
    @JacksonXmlProperty(isAttribute = true)
    private String FaultStartTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class ChannelValue {
    @JacksonXmlProperty(isAttribute = true)
    private String cycle;

    @JacksonXmlProperty(localName = "phs")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Phs> phs;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Phs {
    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(isAttribute = true)
    private String subType;

    @JacksonXmlProperty(isAttribute = true)
    private String subDesc;
    @JacksonXmlProperty(isAttribute = true)
    private String value;

    @JacksonXmlProperty(isAttribute = true)
    private String unit;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DigitalInfo {

    @JacksonXmlProperty(localName = "DigitalStatus")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DigitalStatus> digitalStatus;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DigitalStatus {
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "value")
    private String value;

    @JacksonXmlProperty(localName = "correct")
    private String validate;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SettingInfo {
    @JacksonXmlProperty(localName = "SettingValue")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SettingValue> settingValue;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SettingValue {
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "value")
    private String value;

    @JacksonXmlProperty(localName = "unit")
    private String unit;

    @JacksonXmlProperty(localName = "correct")
    private String validate;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class ResultItem{
    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(isAttribute = true)
    private String value;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class outRimResult{
    @JacksonXmlProperty(localName = "item")
    @JacksonXmlElementWrapper(useWrapping = false)
    private ResultItem[] resultItem;
    private ResultValue result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class ResultValue{
    @JacksonXmlText
    private String value;
}

