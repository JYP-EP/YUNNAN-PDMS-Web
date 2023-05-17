package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gridnt.common.web.entity.BaseEntity;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * 子站对象 tb1041_substation
 *
 * @author gridnt
 * @date 2021-10-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode()
@FieldNameConstants
@TableName("TB1041_SUBSTATION")
public class Tb1041Substation  {


    /**
     * $column.columnComment
     */
    @TableField("F1041_CODE")
    private String f1041Code;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private Double[] value;

    public Double[] getValue(){
        return new Double[]{f1041Longitude,f1041Latitude};
    }

    public String getName(){
        return f1041Name;
    };

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_NAME")
    private String f1041Name;
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
    @TableField("F1040_CODE")
    private String f1040Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_DQDESC")
    private String f1041Dqdesc;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_COMPANY")
    private String f1041Company;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_VOLTAGEH")
    private Long f1041Voltageh;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_VOLTAGEM")
    private Long f1041Voltagem;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_VOLTAGEL")
    private Long f1041Voltagel;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_KEYDESC")
    private String f1041Keydesc;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_TYPE")
    private Long f1041Type;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_OMSID")
    private String f1041Omsid;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_DCID")
    private String f1041Dcid;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_LONGITUDE")
    private Double f1041Longitude;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1041_LATITUDE")
    private Double f1041Latitude;
}