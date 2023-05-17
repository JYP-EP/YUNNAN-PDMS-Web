package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.Excel;
import com.gridnt.common.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * 子站间隔对象 tb1042_bay
 *
 * @author gridnt
 * @date 2021-10-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@TableName("TB1042_BAY")
public class Tb1042Bay extends BaseEntity {

    /**
     * $column.columnComment
     */
    @TableField("F1042_CODE")
    private String f1042Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_CODE")
    private String f1041Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_NAME")
    private String f1042Name;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_DESC")
    private String f1042Desc;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_VOLTAGE")
    private Integer f1042Voltage;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_CONNTYPE")
    private Integer f1042Conntype;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_DEVTYPE")
    private Integer f1042Devtype;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_IEDSOLUTION")
    private Integer f1042Iedsolution;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_KEYDESC")
    private String f1042Keydesc;

}
