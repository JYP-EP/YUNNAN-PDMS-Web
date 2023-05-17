package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

//跳合闸返校诊断

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "STAT")
public class TripDiagXml {
    public static final int ID = 106;
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(localName = "QueryTime")
    private TripDiagQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TripDiagQueryTime{
    @JacksonXmlProperty(isAttribute = true)
    private String start;
    @JacksonXmlProperty(isAttribute = true)
    private String end;
    @JacksonXmlProperty(localName = "Warning")
    @JacksonXmlElementWrapper(useWrapping = false)
    private TripDiagQueryWarning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TripDiagQueryWarning{
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String time;
    @JacksonXmlProperty(localName = "IED")
    @JacksonXmlElementWrapper(useWrapping = false)
    private TripDiagQueryIED[] ied;
    @JacksonXmlProperty(localName = "FaultFile")
    private TripDiagQueryFaultFile faultFile;
    @JacksonXmlProperty(localName = "Result")
    private String result;
    @JacksonXmlProperty(localName = "Suggestion")
    private String suggestion;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TripDiagQueryIED{
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(localName = "Point")
    @JacksonXmlElementWrapper(useWrapping = false)
    private TripDiagQueryPoint[] point;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TripDiagQueryPoint{
    @JacksonXmlProperty(isAttribute = true)
    private String path;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(isAttribute = true)
    private String other;
    @JacksonXmlProperty(localName = "Value")
    private String value;
    @JacksonXmlProperty(localName = "Time")
    private String time;
    @JacksonXmlProperty(localName = "Quality")
    private String quality;
    @JacksonXmlProperty(localName = "Iedname")
    private String iedname;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TripDiagQueryFaultFile{
    @JacksonXmlProperty(localName = "COMTRADE")
    private String COMTRADE;
    @JacksonXmlProperty(localName = "MIDDLE")
    private String MIDDLE;
}