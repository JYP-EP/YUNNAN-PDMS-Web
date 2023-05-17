package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 间隔
 */
@Data
@Accessors(chain = true)
public class CommSta_Bay {
    /**
     * 变电站内的所有间隔
     */
    List<CommSta_IED> ieds;


    private String bayName;
    private String bayCode;
    private String bayDesc;
    private String substationCode;


}
