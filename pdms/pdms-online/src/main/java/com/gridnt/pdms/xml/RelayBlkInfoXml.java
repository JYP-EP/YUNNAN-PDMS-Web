package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

//保护闭锁告警信息

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "STAT")
public class RelayBlkInfoXml {
    public static final int ID = 107;
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(localName = "QueryTime")
    private RelayBlkInfoQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class RelayBlkInfoQueryTime{
    @JacksonXmlProperty(isAttribute = true)
    private String start;
    @JacksonXmlProperty(isAttribute = true)
    private String end;
    @JacksonXmlProperty(localName = "Warning")
    @JacksonXmlElementWrapper(useWrapping = false)
    private RelayBlkInfoWarning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class RelayBlkInfoWarning{
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String time;
    @JacksonXmlProperty(localName = "IED")
    @JacksonXmlElementWrapper(useWrapping = false)
    private RelayBlkInfoIED[] ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class RelayBlkInfoIED{
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(localName = "Point")
    @JacksonXmlElementWrapper(useWrapping = false)
    private RelayBlkInfoPoint[] point;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class RelayBlkInfoPoint{
    @JacksonXmlProperty(isAttribute = true)
    private String path;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(localName = "Value")
    private String value;

    @JacksonXmlProperty(localName = "Time")
    private String time;
}

