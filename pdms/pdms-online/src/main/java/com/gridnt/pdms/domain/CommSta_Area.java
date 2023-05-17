package com.gridnt.pdms.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CommSta_Area {
//    地区集合
    private String areaName;


    private List<CommSta_Subs> subs;
}
