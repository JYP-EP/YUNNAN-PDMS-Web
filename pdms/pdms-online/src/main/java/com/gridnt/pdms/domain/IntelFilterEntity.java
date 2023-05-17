package com.gridnt.pdms.domain;

import com.gridnt.common.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class IntelFilterEntity extends BaseAlertFilterEntity {
	private static final int ALL_ALERT = 0;
	private static final int STAT_ALERT_BEGIN = 8;
	private static final int STAT_ALERT_END = 20;
	private Integer alertType;
	private Integer minType;
	private Integer maxType;

//	public Integer alertType()
//	{
//		return alertType;
//	}
//	public Integer getMinType() {
//		return minType;
//	}

//	public Integer getMinType() {
//		return alertType;
//	}
//
//	public Integer getMaxType() {
//		if(alertType.equals(ALL_ALERT) || alertType.equals(STAT_ALERT_BEGIN)){
//			return STAT_ALERT_END;
//		}
//		else{
//			return maxType;
//		}
//	}
}
