package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

//二次回路故障诊断
@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecCircuitFaultDiagXml {
    public static final int ID = 101;
    @JacksonXmlProperty(isAttribute = true,localName = "type")
    private String type;
    @JacksonXmlProperty(isAttribute = true,localName = "desc")
    private String desc;
    @JacksonXmlProperty(localName = "QueryTime")
    private SecCircuitFaultDiagQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagQueryTime {
    @JacksonXmlProperty(isAttribute = true,localName = "start")
    private String start;
    @JacksonXmlProperty(isAttribute = true,localName = "end")
    private String end;
    @JacksonXmlProperty(localName = "Warning")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagWarning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagWarning {
    @JacksonXmlProperty(isAttribute = true,localName = "type")
    private String type;
    @JacksonXmlProperty(isAttribute = true,localName = "time")
    private String time;
    @JacksonXmlProperty(localName = "IED")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagIED[] ied;
    @JacksonXmlProperty(localName = "Locations")
    private SecCircuitFaultDiagLocations locations;
    @JacksonXmlProperty(localName = "Effects")
    private SecCircuitFaultDiagEffects effects;
    @JacksonXmlProperty(localName = "Suggestions")
    private SecCircuitFaultDiagSuggestions suggestions;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagIED {
    @JacksonXmlProperty(isAttribute = true,localName = "name")
    private String name;
    @JacksonXmlProperty(isAttribute = true,localName = "desc")
    private String desc;
    @JacksonXmlProperty(localName = "Point")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagPoint[] point;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagLocations {
    @JacksonXmlProperty(localName = "Ied")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagLIed[] ied;
    @JacksonXmlProperty(localName = "Slot")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagLocationsData[] slot;
    @JacksonXmlProperty(localName = "Port")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagLocationsData[] port;
    @JacksonXmlProperty(localName = "Cable")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagCable[] cable;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagEffects {
    @JacksonXmlProperty(localName = "Effect")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagEffect[] effect;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagSuggestions{
    @JacksonXmlProperty(localName = "Suggestion")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagEffect[] suggestion;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagPoint {
    @JacksonXmlProperty(isAttribute = true,localName = "path")
    private String path;

    @JacksonXmlProperty(isAttribute = true,localName = "desc")
    private String desc;

    @JacksonXmlProperty(isAttribute = true,localName = "other")
    private String other;
    @JacksonXmlProperty(localName = "Value")
    private String value;
    @JacksonXmlProperty(localName = "Time")
    private String time;
    @JacksonXmlProperty(localName = "Quality")
    private String quality;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagLIed {
    @JacksonXmlProperty(isAttribute = true,localName = "iedName")
    private String iedName;
    @JacksonXmlProperty(isAttribute = true,localName = "iedDesc")
    private String iedDesc;
    @JacksonXmlProperty(isAttribute = true,localName = "resultId")
    private String resultId;
    @JacksonXmlProperty(isAttribute = true,localName = "priority")
    private String priority;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagLocationsData {
    @JacksonXmlProperty(isAttribute = true,localName = "id")
    private String id;
    @JacksonXmlProperty(isAttribute = true,localName = "iedName")
    private String iedName;

    @JacksonXmlProperty(isAttribute = true,localName = "plug")
    private String plug;

    @JacksonXmlProperty(isAttribute = true,localName = "iedDesc")
    private String iedDesc;
    @JacksonXmlProperty(isAttribute = true,localName = "resultId")
    private String resultId;
    @JacksonXmlProperty(isAttribute = true,localName = "priority")
    private String priority;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagCable {
    @JacksonXmlProperty(isAttribute = true,localName = "id")
    private String id;
    @JacksonXmlProperty(isAttribute = true,localName = "startIedName")
    private String startIedName;
    @JacksonXmlProperty(isAttribute = true,localName = "startIedDesc")
    private String startIedDesc;
    @JacksonXmlProperty(isAttribute = true,localName = "endIedName")
    private String endIedName;
    @JacksonXmlProperty(isAttribute = true,localName = "endIedDesc")
    private String endIedDesc;
    @JacksonXmlProperty(isAttribute = true,localName = "cableLen")
    private String cableLen;
    @JacksonXmlProperty(isAttribute = true,localName = "resultId")
    private String resultId;
    @JacksonXmlProperty(isAttribute = true,localName = "priority")
    private String priority;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagEffect{
    @JacksonXmlProperty(isAttribute = true,localName = "desc")
    private String desc;
    @JacksonXmlProperty(isAttribute = true,localName = "resultId")
    private String resultId;
    @JacksonXmlProperty(isAttribute = true,localName = "priority")
    private String priority;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagDataGroup {
    @JacksonXmlProperty(isAttribute = true,localName = "num")
    private String num;

    @JacksonXmlProperty(localName = "data")
    @JacksonXmlElementWrapper(useWrapping = false)
    private SecCircuitFaultDiagData[] SecCircuitFaultDiagData;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class SecCircuitFaultDiagData {
    @JacksonXmlProperty(isAttribute = true)
    private String value;

    @JacksonXmlProperty(isAttribute = true)
    private String datetime;

    @JacksonXmlProperty(isAttribute = true)
    private String quality;
}

