package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 通信状态
 */
@Data
@Accessors(chain = true)
public class Commsta_CommStatusVo {
    private String objectName;
    private String objectCode;
    private String stationCode;
    private String bayCode;
    private Date commDate;
    private int connectionTime;
    private int disConnectionTime;

    private String connTime;
    private String disConnTime;
    private double connRate;

}
