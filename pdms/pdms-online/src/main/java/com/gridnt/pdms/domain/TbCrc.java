package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gridnt.common.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class TbCrc extends BaseEntity {

    private String iedName;

    private String scdCrc;

    private String iedCrc;

    private boolean isConsistent;
}
