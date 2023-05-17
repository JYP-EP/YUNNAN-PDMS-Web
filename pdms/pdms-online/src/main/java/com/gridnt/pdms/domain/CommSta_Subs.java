package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 变电站
 */
@Data
@Accessors(chain = true)
public class CommSta_Subs {

    /**
     * 变电站内的所有间隔
     */
    List<CommSta_Bay> bays;


    private String subName;
    private String substationCode;
    private String areaName;


}
