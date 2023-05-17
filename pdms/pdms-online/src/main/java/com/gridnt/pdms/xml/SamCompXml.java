package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

//同源数据比对

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "STAT")
public class SamCompXml {
    public static final int ID = 103;
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(localName = "QueryTime")
    private  SamCompQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SamCompQueryTime{
    @JacksonXmlProperty(isAttribute = true)
    private String start;
    @JacksonXmlProperty(isAttribute = true)
    private String end;

    @JacksonXmlProperty(localName = "Warning")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SamCompWarning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SamCompWarning{
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String time;
    @JacksonXmlProperty(localName = "Group")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SamCompGroup[] group;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SamCompGroup{
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String time;
    @JacksonXmlProperty(localName = "Channel")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SamCompChannel[] channel;
    @JacksonXmlProperty(localName = "Result")
    private SamCompResult Result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SamCompChannel{
    @JacksonXmlProperty(isAttribute = true)
    private String path;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(isAttribute = true)
    private String other;
    @JacksonXmlProperty(isAttribute = true)
    private String iedName;

    @JacksonXmlProperty(localName = "Data")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SamCompData data;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SamCompData{
    @JacksonXmlProperty(isAttribute = true)
    private String value;
    @JacksonXmlProperty(isAttribute = true)
    private String unit;
    @JacksonXmlProperty(isAttribute = true)
    private String ang;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SamCompResult{
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
}