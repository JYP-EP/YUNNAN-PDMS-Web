package com.gridnt.pdms.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SubstationMapVo {

    private String  id;

    private String name;

    private String gooseRate = "";

    private String mmsRate = "";

    private String onlineRate = "";

    private String smvRate = "";

    private String status = "";

    private String warningNumber;

    private String lockNumber;

    private String procStatusNumber;

    private List<Double> value;

    private List<String> dqSubTree;
}
