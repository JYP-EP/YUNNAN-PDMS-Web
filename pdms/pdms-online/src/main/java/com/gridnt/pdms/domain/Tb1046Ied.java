package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.gridnt.common.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * 保护设备对象 tb1046_ied
 *
 * @author gridnt
 * @date 2021-10-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@TableName("TB1046_IED")
public class Tb1046Ied extends BaseEntity {

    /**
     * $column.columnComment
     */
    @TableField("F1046_CODE")
    private String f1046Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_CODE")
    private String f1042Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1050_CODE")
    private String f1050Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_NAME")
    private String f1046Name;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_DESC")
    private String f1046Desc;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_MANUFACTUROR")
    private String f1046Manufacturor;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_MODEL")
    private String f1046Model;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_CONFIGVERSION")
    private String f1046Configversion;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_AORB")
    private Integer f1046Aorb;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_ISVIRTUAL")
    private Integer f1046Isvirtual;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_TYPE")
    private Integer f1046Type;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_SUBTYPE")
    private Integer f1046Subtype;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_PRIMARYEQUIPMENT")
    private String f1046Primaryequipment;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_DISPATCHNAME")
    private String f1046Dispatchname;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_DEVICEPROPERTY")
    private Integer f1046Deviceproperty;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_RUNDATE")
    private String f1046Rundate;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_PRODUCTIONDATE")
    private String f1046Productiondate;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_PRODUCTIONNO")
    private String f1046Productionno;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_SOFTVERSION")
    private String f1046Softversion;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_SAMPLESTYLE")
    private Integer f1046Samplestyle;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_EXPORTMODEL")
    private Integer f1046Exportmodel;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_DC_RATEDVOL")
    private Integer f1046DcRatedvol;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_CT_RATECURRENT")
    private Integer f1046CtRatecurrent;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_ASSETNATURE")
    private Integer f1046Assetnature;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_LOCALNOTE")
    private Integer f1046Localnote;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_PROTECTIONCATEGORY")
    private Integer f1046Protectioncategory;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_PROTECTIONTYPE")
    private Integer f1046Protectiontype;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_ANALOGNUM")
    private Integer f1046Analognum;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_DIGITALNUM")
    private Integer f1046Digitalnum;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_BOARDNUM")
    private Integer f1046Boardnum;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_ID")
    private String f1046Id;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_SN")
    private String f1046Sn;
}