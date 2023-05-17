package com.gridnt.pdms.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommStat_hisVo {
    private String hcode;
    private String area;
    private String subName;
    private String objectName;
    private String substation;
    private String bay;
    private int ymd;
    private int hmsms;
    private int state;
    private String object;
    private String text;

    private String time;
}
