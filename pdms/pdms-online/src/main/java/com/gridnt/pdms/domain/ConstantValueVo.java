package com.gridnt.pdms.domain;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;
import java.util.Map;

@Data
public class ConstantValueVo {

    /**
     * 所属实例名
     */
    private String ld;

    /**
     * 所属数据集
     */
    private String className;

    /**
     * 当前定值区号
     */
    private String grp;

    /**
     * Map<区,定值>数据
     */
    List<SiVo> siList;
    /**
     * 定值区列表
     */
    List<Map<String,String>> grpList;
}
