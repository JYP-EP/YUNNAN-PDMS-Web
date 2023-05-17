package com.gridnt.pdms.domain;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;


@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Val")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ValVo {


    @XmlAttribute(name = "grp")
    private String grp;

    @XmlValue
    private String Value;
}
