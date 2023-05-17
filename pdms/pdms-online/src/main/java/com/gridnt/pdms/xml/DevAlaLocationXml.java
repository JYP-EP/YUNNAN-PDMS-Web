package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

//装置异常告警定位

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "STAT")
public class DevAlaLocationXml {
    public static final int ID = 105;
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(localName = "QueryTime")
    private DevAlaLocationQueryTime queryTime;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DevAlaLocationQueryTime{
    @JacksonXmlProperty(isAttribute = true)
    private String start;
    @JacksonXmlProperty(isAttribute = true)
    private String end;
    @JacksonXmlProperty(localName = "Warning")
    @JacksonXmlElementWrapper(useWrapping = false)
    private DevAlaLocationWarning[] warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DevAlaLocationWarning{
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlProperty(isAttribute = true)
    private String time;
    @JacksonXmlProperty(localName = "Locations")
    private DevAlaLocationLocations locations;
    @JacksonXmlProperty(localName = "Effects")
    private DevAlaLocationEffects effects;
    @JacksonXmlProperty(localName = "Suggestions")
    private DevAlaLocationSuggestions suggestions;
    @JacksonXmlProperty(localName = "IED")
    @JacksonXmlElementWrapper(useWrapping = false)
    private DevAlaLocationIED[] ied;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DevAlaLocationLocations{
    @JacksonXmlProperty(localName = "Board")
    private DevAlaLocationBoards board;
    @JacksonXmlProperty(localName = "Location")
    private DevAlaLocationLocation location;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DevAlaLocationBoards{
    @JacksonXmlProperty(isAttribute = true)
    private String iedName;
    @JacksonXmlProperty(isAttribute = true)
    private String iedDesc;
    @JacksonXmlProperty(isAttribute = true)
    private String slot;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(isAttribute = true)
    private String priority;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DevAlaLocationLocation{
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(isAttribute = true)
    private String priority;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DevAlaLocationEffects{
    @JacksonXmlProperty(localName = "SLoop")
    private DevAlaLocationLocation sloop;

    @JacksonXmlProperty(localName = "Effect")
    private DevAlaLocationLocation effect;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DevAlaLocationSuggestions{
    @JacksonXmlProperty(localName = "Suggestion")
    @JacksonXmlElementWrapper(useWrapping = false)
    private DevAlaLocationLocation[] suggestion;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DevAlaLocationIED{
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(localName = "Point")
    @JacksonXmlElementWrapper(useWrapping = false)
    private DevAlaLocationPoint[] point;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DevAlaLocationPoint{
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
    @JacksonXmlProperty(localName = "AlaMeaning")
    private String alaMeaning;
    @JacksonXmlProperty(localName = "Level")
    private String level;
}