package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

/**
 * 【请填写功能名称】对象 TB1053_PHYSCONNVIEW
 *
 * @author gridnt
 * @date 2022-11-03
 */
@Data
@Accessors(chain = true)
@FieldNameConstants
@TableName("TB1053_PHYSCONNVIEW")
public class Physconnview {

    /**
     * $column.columnComment
     */
    @Id
    @TableField("F1053_CODE")
    private String f1053Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1053_DESC")
    private String f1053Desc;
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
    @TableField("F1048_CODE_A")
    private String f1048CodeA;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1048_NO_A")
    private String f1048NoA;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1048_CODE_B")
    private String f1048CodeB;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1048_NO_B")
    private String f1048NoB;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1047_CODE_A")
    private String f1047CodeA;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1047_CODE_B")
    private String f1047CodeB;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_CODE_A")
    private String f1046CodeA;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_CODE_B")
    private String f1046CodeB;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1053_TYPE")
    private Long f1053Type;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1016_STATUSCODE")
    private String f1016Statuscode;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1051_NAME")
    private String f1051Name;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1053_AORB")
    private Long f1053Aorb;

    @Transient
    @TableField("F1052_NO")
    private Integer f1052No;

    @Transient
    @TableField("F1046_NAME_A")
    private String f1046NameA;

    @Transient
    @TableField("F1046_NAME_B")
    private String f1046NameB;

    @Transient
    @TableField("F1046_DESC_A")
    private String f1046DescA;

    @Transient
    @TableField("F1046_DESC_B")
    private String f1046DescB;

    /**
     * 发送端光强
     */
    @Transient
    @TableField("F1006_CODE_A")
    private String f1006CodeA;

    /**
     * 接收端光强
     */
    @Transient
    @TableField("F1006_CODE_B")
    private String f1006CodeB;
}
