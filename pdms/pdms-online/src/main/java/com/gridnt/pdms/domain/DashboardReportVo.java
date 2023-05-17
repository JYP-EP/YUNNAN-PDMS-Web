package com.gridnt.pdms.domain;

import lombok.Data;
import org.apache.poi.ss.formula.functions.Count;

@Data
public class DashboardReportVo {

   private String F1041DESC;

   private String substationCode;

   // 回
   private Long aCount;
   // 状
   private Long bCount;
   // 采
   private Long cCount;
   // 定
   private Long dCount;
   // 异
   private Long eCount;
   // 跳
   private Long fCount;


}
