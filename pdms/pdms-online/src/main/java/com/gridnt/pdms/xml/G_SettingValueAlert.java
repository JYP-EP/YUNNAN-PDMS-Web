package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

//定值告警
@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class G_SettingValueAlert {
    public static final int ID = 104;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(localName = "QueryTime")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_S_QueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_S_QueryTime {
    @JacksonXmlProperty(isAttribute = true)
    private String start;

    @JacksonXmlProperty(isAttribute = true)
    private String end;

    @JacksonXmlProperty(localName = "IED")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_S_IED[] ied;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_S_IED {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "Warning")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_S_Warning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_S_Warning {
    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String time;

    @JacksonXmlProperty(isAttribute = true)
    private String subType;

    @JacksonXmlProperty(localName = "Point")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_ComparePoint[] points;

    @JacksonXmlProperty(localName = "CompareTemplate")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_S_CompareTemplate compareTemplate;

    @JacksonXmlProperty(isAttribute = true)
    private String Result;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_ComparePoint{
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
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_S_CompareTemplate {
    @JacksonXmlProperty(localName = "Point")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_ComparePoint[] points;

    @JacksonXmlProperty(localName = "Value")
    private String value;

    @JacksonXmlProperty(localName = "Time")
    private String time;

}
