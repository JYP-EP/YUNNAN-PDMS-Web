package com.gridnt.pdms.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "DSC")
@XmlAccessorType(XmlAccessType.FIELD)
public class SettingFileContentXml {

    //定值区集合
    @XmlElement(name = "Settings")
    private List<SettingDataVo> settings;
}
