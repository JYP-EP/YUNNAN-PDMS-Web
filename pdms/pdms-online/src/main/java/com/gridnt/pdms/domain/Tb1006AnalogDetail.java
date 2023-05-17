package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Tb1006AnalogDetail {
    /**
     * $column.columnComment
     */
    @Id
    @TableField("F1006_CODE")
    private String f1006Code;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_DESC")
    private String f1006Desc;
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
    @TableField("F1041_CODE")
    private String f1041Code;
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
    @TableField("F1006_PARENT")
    private String f1006Parent;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1046_CODE")
    private String f1046Code;
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
    @TableField("F1006_DAQ")
    private String f1006Daq;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_REF")
    private String f1006Ref;
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
    @TableField("F1006_SOURCE")
    private Long f1006Source;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_CALCFLAG")
    private Long f1006Calcflag;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_K")
    private Long f1006K;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_B")
    private Long f1006B;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_ZERODBAND")
    private Long f1006Zerodband;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_OVERFLOW")
    private Long f1006Overflow;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_LOWFLOW")
    private Long f1006Lowflow;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_MAXINC")
    private Long f1006Maxinc;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_HIALARM")
    private Long f1006Hialarm;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_LOALARM")
    private Long f1006Loalarm;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_SAVEPERIOD")
    private Long f1006Saveperiod;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_DEADTIME")
    private Long f1006Deadtime;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_ALARMLEVEL")
    private Long f1006Alarmlevel;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_SAMPLECPMAXINC")
    private Long f1006Samplecpmaxinc;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_SAMPLEPERIOD")
    private Long f1006Sampleperiod;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_SAMPLEPTNUM")
    private Long f1006Sampleptnum;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_CHANGERATE")
    private Long f1006Changerate;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_RESERVE")
    private Long f1006Reserve;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_ALARMDURATION")
    private Long f1006Alarmduration;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_SAMPLECPCYCLE")
    private Long f1006Samplecpcycle;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1006_SAMPLECPNUM")
    private Long f1006Samplecpnum;


    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_VAL")
    private Long f1017Val;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_REVVAL")
    private Long f1017Revval;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_ANGLE")
    private Long f1017Angle;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_QUALITY")
    private Long f1017Quality;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_TIME")
    private Long f1017Time;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_DEADTIME")
    private Long f1017Deadtime;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_PREPROCTIME")
    private Long f1017Preproctime;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_INVALID")
    private Integer f1017Invalid;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_DEADFLAG")
    private Integer f1017Deadflag;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_NOALARM")
    private Integer f1017Noalarm;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_UPDATE")
    private Integer f1017Update;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_MANSET")
    private Integer f1017Manset;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_OVERLIMITFLAG")
    private Integer f1017Overlimitflag;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_SAMPLECPFLAG")
    private Integer f1017Samplecpflag;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_TRENDCPFLAG")
    private Integer f1017Trendcpflag;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_DEFECTFLAG")
    private Integer f1017Defectflag;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_LIMITALARMTIME")
    private Long f1017Limitalarmtime;
    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}" , readConverterExp = "$column.readConverterExp()")
    @TableField("F1017_OLDVAL")
    private Long f1017Oldval;
}
