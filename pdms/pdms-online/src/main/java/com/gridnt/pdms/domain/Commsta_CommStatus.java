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
@TableName(value = "TB_CommStatusStatistics")
public class Commsta_CommStatus {
    /**
     *对象code
     */
    @TableField("objectCode")
    private String objectCode;
    /**
     *站
     */
    @TableField("stationCode")
    private String stationCode;
    /**
     *间隔
     */
    @TableField("bayCode")
    private String bayCode;
    /**
     *日期
     */
    @TableField("commDate")
    private Date commDate;
    /**
     *连接成功时间s
     */
    @TableField("connectionTime")
    private int connectionTime;
    /**
     *连接断开时间s
     */
    @TableField("disConnectionTime")
    private int disConnectionTime;

}
