package com.gridnt.pdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gridnt.pdms.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbIntelligentWarningMapper extends BaseMapper<TbIntelligentWarning> {
    Page<IntelAlertEntity> getIntelAlert(Page<IntelAlertEntity> page, @Param("intelFilter") IntelFilterEntity intelFilter);

    List<IntelAlertStat> getIntelAlertStat(@Param("intelFilter") IntelFilterEntity intelFilter);

    Page<FrequentAlertEntity> getFrequentAlert(Page<FrequentAlertEntity> page, @Param("intelFilter") IntelFilterEntity intelFilter);

    List<FrequentStat> getFrequentStat(@Param("intelFilter") IntelFilterEntity intelFilter);

    Page<ProtectionTunnelAlertEntity> getProtectionTunnelAlert(Page<ProtectionTunnelAlertEntity> page,
                                                               @Param("intelFilter") TunnelFilterEntity intelFilter);

    Page<BlockingAlertEntity> getBlockingAlert(Page<BlockingAlertEntity> page,
                                               @Param("intelFilter") TunnelFilterEntity intelFilter);

    List<BlockingStat> getBlockingStat(@Param("intelFilter") TunnelFilterEntity intelFilter);

    FileContextEntity getFileContext(@Param("key") String key);

    //    区外分析
    Page<OutRimAlertEntity> getOutRimAlert(Page<OutRimAlertEntity> page, @Param("intelFilter") IntelFilterEntity intelFilter);

    Page<TransformerCurrentEntity> getTransformerCurrentAlert(Page<TransformerCurrentEntity> page, @Param("intelFilter") IntelFilterEntity intelFilter);



    List<CommSta_Bay> getAllBay();

    List<CommSta_IED> getAllIED();


    List<CommSta_Subs> getAllSubs();

    List<CommSta_Area> getAllArea();



    List<Commsta_CommStatusVo> getAllCommStatus(CommSta_queryDate queryDate);
    List<CommStat_hisVo> getAllHisEvent(CommSta_queryDate queryDate);

    List<CommStat_hisVo> getAreaHisEvent(CommSta_queryDate queryDate);
    List<Commsta_CommStatusVo> getAreaCommStatus(CommSta_queryDate queryDate);

    List<CommStat_hisVo> getSubHisEvent(CommSta_queryDate queryDate);
    List<Commsta_CommStatusVo> getSubCommStatus(CommSta_queryDate queryDate);

    List<CommStat_hisVo> getBayHisEvent(CommSta_queryDate queryDate);
    List<Commsta_CommStatusVo> getBayCommStatus(CommSta_queryDate queryDate);

    List<CommStat_hisVo> getIedHisEvent(CommSta_queryDate queryDate);
    List<Commsta_CommStatusVo> getIedCommStatus(CommSta_queryDate queryDate);

    List<IntelAlertLevel> getIntelAlertLevel(@Param("intelFilter") TunnelFilterEntity intelFilter);

    List<DashboardReportVo> getJudgeReportStatistics();

    Integer getLatestVisitReportNumber();

    Integer getSubstationNumber();

    List<Tb1041Substation> getNotVisitStationList();
}
