package com.gridnt.pdms.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("TB_INTELLIGENT_WARNING")
public class TbIntelligentWarning extends BaseEntity {
	private String id;

	@TableField("substation_code")
	private String substationCode;

	@TableField("ied_code")
	private String iedCode;

	@TableField("triggertime")
	private String triggerTime;

	@TableField("alerttype")
	private int alertType;

	@TableField("source")
	private int source;

	@TableField("fileid")
	private String fileId;

	@TableField("file_status")
	private int status;
}
