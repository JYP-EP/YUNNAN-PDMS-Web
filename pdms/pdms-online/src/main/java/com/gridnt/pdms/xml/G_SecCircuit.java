package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "STAT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class G_SecCircuit {

    public static final int ID = 10;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String desc;
    @JacksonXmlProperty(localName = "QueryTime")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_QueryTime queryTime;
}


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_QueryTime {
    @JacksonXmlProperty(isAttribute = true)
    private String start;

    @JacksonXmlProperty(isAttribute = true)
    private String end;

    @JacksonXmlProperty(localName = "Warning")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_Warning warning;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_Warning {
    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String time;

    @JacksonXmlProperty(localName = "IED")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_IED[] ied;

    @JacksonXmlProperty(localName = "Locations")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_Locations[] location;

    @JacksonXmlProperty(localName = "Effects")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_Effects[] effects;

    @JacksonXmlProperty(localName = "Suggestions")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_Suggestions[] suggestions;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_IED {
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(localName = "Point")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_Point[] point;
}


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_Point {
    @JacksonXmlProperty(isAttribute = true)
    private String path;

    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(isAttribute = true)
    private String other;
    @JacksonXmlProperty(isAttribute = true)
    private String Value;

    @JacksonXmlProperty(isAttribute = true)
    private String Time;

    @JacksonXmlProperty(isAttribute = true)
    private String Quality;
}


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_Locations {
    @JacksonXmlProperty(localName = "Ied")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_L_Ied[] ied;

    @JacksonXmlProperty(localName = "Slot")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_L_Slot[] slot;
    @JacksonXmlProperty(localName = "Port")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_L_Port[] port;
    @JacksonXmlProperty(localName = "Cable")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_L_Cable[] cable;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_L_Ied {
    @JacksonXmlProperty(isAttribute = true)
    private String iedName;

    @JacksonXmlProperty(isAttribute = true)
    private String iedDesc;

    @JacksonXmlProperty(isAttribute = true)
    private String resultId;

    @JacksonXmlProperty(isAttribute = true)
    private String priority;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_L_Slot {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String iedName;

    @JacksonXmlProperty(isAttribute = true)
    private String iedDesc;

    @JacksonXmlProperty(isAttribute = true)
    private String resultId;

    @JacksonXmlProperty(isAttribute = true)
    private String priority;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_L_Port {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String iedName;

    @JacksonXmlProperty(isAttribute = true)
    private String iedDesc;

    @JacksonXmlProperty(isAttribute = true)
    private String plug;

    @JacksonXmlProperty(isAttribute = true)
    private String resultId;

    @JacksonXmlProperty(isAttribute = true)
    private String priority;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_L_Cable {
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String startIedName;

    @JacksonXmlProperty(isAttribute = true)
    private String startIedDesc;

    @JacksonXmlProperty(isAttribute = true)
    private String endIedName;
    @JacksonXmlProperty(isAttribute = true)
    private String endIedDesc;

    @JacksonXmlProperty(isAttribute = true)
    private String cableLen;
    @JacksonXmlProperty(isAttribute = true)
    private String resultId;
    @JacksonXmlProperty(isAttribute = true)
    private String priority;
}


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_Effects {
    @JacksonXmlProperty(localName = "Effect")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_Effect[] effect;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_Suggestions {
    @JacksonXmlProperty(localName = "Suggestion")
    @JacksonXmlElementWrapper(useWrapping = false)
    private G_Suggestion[] suggestion;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_Effect {
    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(isAttribute = true)
    private String resultId;

    @JacksonXmlProperty(isAttribute = true)
    private String priority;
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class G_Suggestion {
    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(isAttribute = true)
    private String resultId;

    @JacksonXmlProperty(isAttribute = true)
    private String priority;
}
