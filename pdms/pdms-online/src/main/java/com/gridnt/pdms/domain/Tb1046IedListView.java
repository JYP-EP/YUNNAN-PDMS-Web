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
 * 保护设备对象信息 TB1046_IEDLISTVIEW
 *
 * @author gridnt
 * @date 2023-03-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@TableName("TB1046_IEDLISTVIEW")
public class Tb1046IedListView extends BaseEntity {
    /**
     * $column.columnComment
     */
    @TableField("F1046_CODE")
    private String f1046Code;
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
    @TableField("F1041_DESC")
    private String f1041Desc;
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
    @TableField("F1042_DESC")
    private String f1042Desc;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_VOLTAGE")
    private String F1042Voltage;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1042_VOLTAGEDESC")
    private String F1042VoltageDesc;
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
    @TableField("F1050_DESC")
    private String f1050Desc;
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
    @TableField("F1046_CRCBASE")
    private String f1046CrcBase;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_AORB")
    private String f1046Aorb;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_RUNSTATUSCODE")
    private String f1046RunStatusCode;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_COMMSTATUSCODE")
    private String f1046CommStatusCode;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_GOOSESTATUSCODE")
    private String f1046GooseStatusCode;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_SVSTATUSCODE")
    private String f1046SVStatusCode;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_SGSTATUSCODE")
    private String f1046SGStatusCode;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_CRCSTATUSCODE")
    private String f1046CrcStatusCode;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_CRCVALCODE")
    private String f1046CrcValCode;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_VERVALCODE")
    private String f1046VerValCode;
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
    @TableField("F1046_OMSID")
    private String f1046OmsId;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_DCID")
    private String f1046DcId;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_SN")
    private String f1046Sn;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_PROTECTIONCATEGORY")
    private String f1046ProtectionCategory;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_COUNTRY")
    private String f1046Country;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_TYPE")
    private String f1046Type;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_PRODUCTIONNO")
    private String f1046ProductionNo;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_PRODUCTIONDATE")
    private String f1046ProductionDate;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1049_CODE")
    private String F1049Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1049_DESC")
    private String F1049Desc;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_SOFTVERSION")
    private String F1046SoftVersion;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_VERSTATUSCODE")
    private String F1046VerStatusCode;
}
