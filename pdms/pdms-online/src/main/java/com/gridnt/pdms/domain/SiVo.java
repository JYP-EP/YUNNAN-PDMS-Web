package com.gridnt.pdms.domain;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "SI")
@XmlAccessorType(XmlAccessType.FIELD)
public class SiVo {

    @XmlAttribute(name = "desc")
    private String desc;


    @XmlAttribute(name = "type")
    private String type;


    @XmlAttribute(name = "unit")
    private String unit;


    @XmlAttribute(name = "min")
    private String min;


    @XmlAttribute(name = "max")
    private String max;


    @XmlAttribute(name = "step")
    private String step;


    @XmlAttribute(name = "ref")
    private String ref;


    @XmlElement(name="Val")
    private List<ValVo> valList;

    private String tag;
}
