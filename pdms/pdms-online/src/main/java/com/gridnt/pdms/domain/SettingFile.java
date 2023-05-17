package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import org.apache.ibatis.type.BlobTypeHandler;

import java.util.Date;

@Data
@TableName("SETTINGFILE")
public class SettingFile {

    private String id;

    @TableField("F1046_CODE")
    private String F1046CODE;

    @TableField("sgcbFileName")
    private String sgcbFileName;

    @TableField("receiveTime")
    private Date receiveTime;

    @TableField("operationTime")
    private Date operationTime;

    private String operator;
    private Integer state;
    private Integer source;
    private Integer report;

    @TableField(value = "content",typeHandler = BlobTypeHandler.class)
    private byte[] content;


}
