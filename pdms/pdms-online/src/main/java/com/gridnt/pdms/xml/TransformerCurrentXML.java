package com.gridnt.pdms.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import java.util.List;

//主变变中
@Data
@JacksonXmlRootElement(localName = "MainProtectionReport")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransformerCurrentXML {
    public static final int ID = 5;

    @JacksonXmlProperty(isAttribute = true)
    private String result;


    @JacksonXmlProperty(localName = "deviceInfo")
    private DeviceInfo deviceInfo;

    @JacksonXmlProperty(localName = "station")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Station> station;
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DeviceInfo{
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;

}


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Station{
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(localName = "ied")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Ied> ied;

}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Ied{
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String desc;

    @JacksonXmlProperty(localName = "phs")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Phs> phs;
}
