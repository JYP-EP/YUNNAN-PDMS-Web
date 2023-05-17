package com.gridnt.pdms.xml;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

//状态监测预警
@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusMonitorXml {
    public static final int ID = 102;

    @JacksonXmlProperty(isAttribute = true,localName = "type")
    private String type;
    @JacksonXmlProperty(isAttribute = true,localName = "desc")
    private String desc;

    @JacksonXmlProperty(localName = "QueryTime")
    private StatusMonitorQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusMonitorQueryTime{
    @JacksonXmlProperty(isAttribute = true,localName = "start")
    private String start;
    @JacksonXmlProperty(isAttribute = true,localName = "end")
    private String end;
    @JacksonXmlProperty(localName = "IED")
    @JacksonXmlElementWrapper(useWrapping = false)
    private  StatusMonitorIED[] ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusMonitorIED{
    @JacksonXmlProperty(isAttribute = true,localName = "name")
    private String name;
    @JacksonXmlProperty(isAttribute = true,localName = "desc")
    private String desc;
    @JacksonXmlProperty(localName = "Warning")
    @JacksonXmlElementWrapper(useWrapping = false)
    private StatusMonitorWarning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusMonitorWarning{
    @JacksonXmlProperty(isAttribute = true,localName = "type")
    private String type;
    @JacksonXmlProperty(isAttribute = true,localName = "time")
    private String time;
    @JacksonXmlProperty(isAttribute = true,localName = "subType")
    private String subtype;
    @JacksonXmlProperty(localName = "Point")
    @JacksonXmlElementWrapper(useWrapping = false)
    private StatusMonitorPoint[] point;
    @JacksonXmlProperty(localName = "Policy")
    private StatusMonitorPolicy policy;
    @JacksonXmlProperty(localName = "Result")
    private String  result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusMonitorPoint{
    @JacksonXmlProperty(isAttribute = true,localName = "path")
    private String path;
    @JacksonXmlProperty(isAttribute = true,localName = "desc")
    private String desc;
    @JacksonXmlProperty(isAttribute = true,localName = "other")
    private String other;
    @JacksonXmlProperty(localName = "Datagrp")
    @JacksonXmlElementWrapper(useWrapping = false)
    private StatusMonitorDatagrp datagrp;
    @JacksonXmlProperty(localName = "Highlimit")
    @JacksonXmlElementWrapper(useWrapping = false)
    private StatusMonitorValue highlimit;
    @JacksonXmlProperty(localName = "Lowlimit")
    @JacksonXmlElementWrapper(useWrapping = false)
    private StatusMonitorValue lowlimit;
    @JacksonXmlProperty(localName = "Alarm")
    private String  alarm;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusMonitorDatagrp{
    @JacksonXmlProperty(isAttribute = true,localName = "num")
    private String num;
    @JacksonXmlProperty(localName = "Data")
    @JacksonXmlElementWrapper(useWrapping = false)
    private StatusMonitorData[] data;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusMonitorData{
    @JacksonXmlProperty(isAttribute = true,localName = "value")
    private String value;
    @JacksonXmlProperty(isAttribute = true,localName = "datetime")
    private String datetime;
    @JacksonXmlProperty(isAttribute = true,localName = "quality")
    private String quality;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusMonitorValue{
    @JacksonXmlProperty(isAttribute = true,localName = "value")
    private String value;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusMonitorPolicy{

    @JacksonXmlProperty(localName = "Condition")
    @JacksonXmlElementWrapper(useWrapping = false)
    private StatusMonitorCondition[] condition;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusMonitorCondition{
    @JacksonXmlProperty(isAttribute = true,localName = "desc")
    private String desc;
    @JacksonXmlText
    private String condition;

}
