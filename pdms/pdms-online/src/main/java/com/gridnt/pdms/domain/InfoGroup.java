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
@XmlRootElement(name = "InfoGrp")
@XmlAccessorType(XmlAccessType.FIELD)
public class InfoGroup {

   @XmlAttribute(name = "desc")
   private String desc;

   @XmlAttribute(name = "setGrpNum")
   private String setGrpNum;

   @XmlAttribute(name = "itemNum")
   private String itemNum;

   @XmlAttribute(name = "remote")
   private String remote;

   @XmlElement(name = "SI")
   private List<SiVo> siList;


}
