package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

//对应表实体类
@Data
@Accessors(chain = true)
@TableName(value = "TB_HIS_EVENT")
public class CommSta_HisEvent {
    /**
     * code
     */
    @TableField("CODE")
    private String CODE;
    /**
     * 所属变电站
     */
    @TableField("SUBSTATION")
    private String SUBSTATION;
    /**
     * 间隔
     */
    @TableField("BAY")
    private String BAY;
    /**
     * 事件分类名
     */
    @TableField("SORT")
    private int SORT;
    /**
     * 事项类型
     */
    @TableField("TYPE")
    private int TYPE;
    /**
     * 事项时间:年月日
     */
    @TableField("YMD")
    private int YMD;
    /**
     * ：时分秒毫秒
     */
    @TableField("HMSMS")
    private int HMSMS;
    /**
     * 电压等级
     */
    @TableField("GRADE")
    private int GRADE;
    /**
     * 状态
     */
    @TableField("STATE")
    private int STATE;
    /**
     * 值
     */
    @TableField("VAL")
    private float VAL;
    @TableField("OBJECT")
    private String OBJECT;
    @TableField("MEMBER0")
    private String MEMBER0;
    @TableField("TEXT")
    private String TEXT;
    @TableField("QUALITY")
    private int QUALITY;
    @TableField("BASETYPE")
    private int BASETYPE;

}
