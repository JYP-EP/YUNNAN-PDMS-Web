package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.ruoyi.common.annotation.Excel;
import com.gridnt.common.web.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;

/**
 * 【请填写功能名称】对象 TB1063_CIRCUITVIEW
 *
 * @author gridnt
 * @date 2022-10-31
 */
@Data
@Accessors(chain = true)
@FieldNameConstants
@TableName("TB1063_CIRCUITVIEW")
public class Tb1063Circuitview  {

    /**
     * $column.columnComment
     */
    @Id
    @TableField("F1063_CODE")
    private String f1063Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1063_DESC")
    private String f1063Desc;
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
    @TableField("F1046_CODE_IEDSEND")
    private String f1046CodeIedsend;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_DESC_IEDSEND")
    private String f1046DescIedsend;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1061_CODE_PSEND")
    private String f1061CodePsend;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1061_DESC_PSEND")
    private String f1061DescPsend;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1061_INDEX_PSEND")
    private Long f1061IndexPsend;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1061_REFADDR_PSEND")
    private String f1061RefaddrPsend;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1064_CODE_SEND")
    private String f1064CodeSend;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1016_CODE_STRAPSEND")
    private String f1016CodeStrapsend;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_CODE_IEDRECV")
    private String f1046CodeIedrecv;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_DESC_IEDRECV")
    private String f1046DescIedrecv;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1062_CODE_PRECV")
    private String f1062CodePrecv;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1062_DESC_PRECV")
    private String f1062DescPrecv;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1062_REFADDR_PRECV")
    private String f1062RefaddrPrecv;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1064_CODE_RECV")
    private String f1064CodeRecv;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1016_CODE_STRAPRECV")
    private String f1016CodeStraprecv;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1065_CODE")
    private String f1065Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1065_TYPE")
    private Long f1065Type;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1016_CODE_LOGICALLNK")
    private String f1016CodeLogicallnk;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1061_CODE_CONVCHK1")
    private String f1061CodeConvchk1;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1061_CODE_CONVCHK2")
    private String f1061CodeConvchk2;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1011_NO")
    private Long f1011No;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1063_TYPE")
    private Long f1063Type;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1063_RESERVE")
    private Long f1063Reserve;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1065_CBCODE")
    private String f1065Cbcode;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1016_CODE_CB")
    private String f1016CodeCb;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1016_CODE_VALUE")
    private String f1016CodeValue;
}
