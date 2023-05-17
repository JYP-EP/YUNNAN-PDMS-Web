package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName(value = "TB1046_IED")
public class CommSta_IED {

    private String bayCode;

    private String iedCode;

    private String iedName;

    private String iedDesc;
}
