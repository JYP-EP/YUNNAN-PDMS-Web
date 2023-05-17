package com.gridnt.pdms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gridnt.pdms.domain.*;

import java.util.List;
import java.util.Map;

public interface ITbIntelligentWarningService extends IService<TbIntelligentWarning> {
    Page<IntelAlertEntity> getIntelAlert(Page<IntelAlertEntity> page, IntelFilterEntity intelFilter);

    List<IntelAlertStat> getIntelAlertStat(IntelFilterEntity intelFilter);

    Page<FrequentAlertEntity> getFrequentAlert(Page<FrequentAlertEntity> page, IntelFilterEntity intelFilter);

    List<FrequentStat> getFrequentStat(IntelFilterEntity intelFilter);

    Page<ProtectionTunnelAlertEntity> getProtectionTunnelAlert(Page<ProtectionTunnelAlertEntity> page, TunnelFilterEntity intelFilter);

    Page<BlockingAlertEntity> getBlockingAlert(Page<BlockingAlertEntity> page, TunnelFilterEntity intelFilter);

    List<BlockingStat> getBlockingStat(TunnelFilterEntity intelFilter);

    FileContextEntity getFileContext(String key);

    List<DivisionNode> getDivision();

    //    区外分析告警列表
    Page<OutRimAlertEntity> getOutRimAlert(Page<OutRimAlertEntity> page, IntelFilterEntity intelFilter);

    //    主变变中电流告警
    Page<TransformerCurrentEntity> getTransformerCurrentAlert(Page<TransformerCurrentEntity> page, IntelFilterEntity intelFilter);

    public Map<String, Object> getCommStat(CommSta_queryDate queryDate);

    List<IntelAlertLevel> getIntelAlertLevel(TunnelFilterEntity intelFilter);

    List<DashboardReportVo> getJudgeReportStatistics();

    String getVisitRate();

    List<Tb1041Substation> getNotVisitStationList();

    List<Tb1041Substation> getSubstationList();


}
