package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName(value = "TB_OBJECT_DESC")
public class CommSta_Object {
    /**
     *对象code
     */
    @TableField("CODE")
    private String code;
    /**
     *对象code
     */
    @TableField("DSCB")
    private String dscb;
    /**
     *对象code
     */
    @TableField("IED")
    private String ied;
    /**
     *对象code
     */
    @TableField("TYPE")
    private String type;
}
