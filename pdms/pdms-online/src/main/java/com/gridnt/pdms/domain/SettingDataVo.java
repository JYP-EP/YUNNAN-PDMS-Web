package com.gridnt.pdms.domain;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 定值返回vo
 */
@Data
@XmlRootElement(name = "Settings")
@XmlAccessorType(XmlAccessType.FIELD)
public class SettingDataVo {

    // 所属实例名
    @XmlAttribute(name = "ld")
    private String instanceName;

    //所属数据集
    @XmlAttribute(name = "class")
    private String dataSet;

    //定值组
    @XmlAttribute(name = "infoGrpNum")
    private String infoGrpNum;

    //定值组
    @XmlAttribute(name = "grp")
    private String grp;

    //定值区集合
    @XmlElement(name = "InfoGrp")
    private List<InfoGroup> infoGroupList;


}
