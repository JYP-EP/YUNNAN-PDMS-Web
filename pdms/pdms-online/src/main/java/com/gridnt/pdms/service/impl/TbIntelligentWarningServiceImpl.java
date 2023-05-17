package com.gridnt.pdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gridnt.pdms.domain.*;
import com.gridnt.pdms.mapper.Tb1041SubstationMapper;
import com.gridnt.pdms.service.ITb1041SubstationService;
import com.gridnt.pdms.service.ITb1046IedService;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gridnt.pdms.mapper.TbIntelligentWarningMapper;
import com.gridnt.pdms.service.ITbIntelligentWarningService;

import javax.annotation.Resource;
import java.text.NumberFormat;
import java.util.*;

@Service
@DataSource(value = DataSourceType.SLAVE)
public class TbIntelligentWarningServiceImpl
        extends ServiceImpl<TbIntelligentWarningMapper, TbIntelligentWarning>
        implements ITbIntelligentWarningService {

    @Resource
    private TbIntelligentWarningMapper alertMapper;
    @Resource
    private Tb1041SubstationMapper substationMapper;

    @Autowired
    private ITb1046IedService iedService;

    @Autowired
    private ITb1041SubstationService substationService;

    @Override
    public Page<IntelAlertEntity> getIntelAlert(Page<IntelAlertEntity> page, IntelFilterEntity intelFilter) {
        return alertMapper.getIntelAlert(page, intelFilter);
    }

    @Override
    public List<IntelAlertStat> getIntelAlertStat(IntelFilterEntity intelFilter) {
        return alertMapper.getIntelAlertStat(intelFilter);
    }

    @Override
    public Page<FrequentAlertEntity> getFrequentAlert(Page<FrequentAlertEntity> page, IntelFilterEntity intelFilter) {
        return alertMapper.getFrequentAlert(page, intelFilter);
    }

    @Override
    public List<FrequentStat> getFrequentStat(IntelFilterEntity intelFilter) {
        return alertMapper.getFrequentStat(intelFilter);
    }

    @Override
    public Page<ProtectionTunnelAlertEntity> getProtectionTunnelAlert(Page<ProtectionTunnelAlertEntity> page, TunnelFilterEntity intelFilter) {
        return alertMapper.getProtectionTunnelAlert(page, intelFilter);
    }

    @Override
    public Page<BlockingAlertEntity> getBlockingAlert(Page<BlockingAlertEntity> page, TunnelFilterEntity intelFilter) {
        return alertMapper.getBlockingAlert(page, intelFilter);
    }

    @Override
    public List<BlockingStat> getBlockingStat(TunnelFilterEntity intelFilter) {
        return alertMapper.getBlockingStat(intelFilter);
    }

    @Override
    public FileContextEntity getFileContext(String key) {
        return alertMapper.getFileContext(key);
    }

    @Override
    public List<DivisionNode> getDivision() {
        List<String> divisions = substationMapper.getDivision();
        if (divisions != null && divisions.size() > 0) {
            List<DivisionNode> result = new ArrayList<>();
            for (String division : divisions) {
                DivisionNode rootNode = new DivisionNode(division);
                List<DivisionNode> substationNode = substationService.getSubstationByDivision(division);
                for (DivisionNode node : substationNode) {
                    List<DivisionNode> deviceNode = iedService.getIntelDeviceBySubstation(node.getId());
                    node.setChildren(deviceNode);
                }
                rootNode.setChildren(substationNode);
                result.add(rootNode);
            }
            return result;
        }
        return null;
    }

    @Override
    public Page<OutRimAlertEntity> getOutRimAlert(Page<OutRimAlertEntity> page, IntelFilterEntity intelFilter) {
        return alertMapper.getOutRimAlert(page, intelFilter);
    }

    @Override
    public Page<TransformerCurrentEntity> getTransformerCurrentAlert(Page<TransformerCurrentEntity> page, IntelFilterEntity intelFilter) {
        return alertMapper.getTransformerCurrentAlert(page, intelFilter);
    }

    @Override
    public Map<String, Object> getCommStat(CommSta_queryDate queryDate) {

        /**
         * 查询所有的装置，变电站跟地区
         */
        List<CommStat_hisVo> allHisEvent = new ArrayList<>();
        List<Commsta_CommStatusVo> allComm = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        List<CommSta_Area> areas = alertMapper.getAllArea();
        List<CommSta_Subs> subs = alertMapper.getAllSubs();
        List<CommSta_Bay> bays = alertMapper.getAllBay();
        List<CommSta_IED> ieds = alertMapper.getAllIED();
        for (CommSta_Subs s : subs) {
            List<CommSta_Bay> bs = new ArrayList<>();
            for (CommSta_Bay b : bays) {
                List<CommSta_IED> is = new ArrayList<>();
                for (CommSta_IED i : ieds) {
                    if (i.getBayCode().equals(b.getBayCode())) {
                        is.add(i);
                    }
                }
                b.setIeds(is);
                if (b.getSubstationCode().equals(s.getSubstationCode())) {
                    bs.add(b);
                }
            }
            s.setBays(bs);
        }
        if (areas.size() > 0) {
            areas.forEach(a -> {
                if (a != null) {
                    List<CommSta_Subs> subItem = new ArrayList<>();
                    if (subs.size() > 0) {
                        subs.forEach(s -> {
                            if (s != null) {
                                if (s.getAreaName() != null && !s.getAreaName().equals("")) {
                                    if (s.getAreaName().equals(a.getAreaName())) {
                                        subItem.add(s);
                                    }
                                }
                            }
                        });
                        a.setSubs(subItem);
                    }
                }
            });
        }

        if (queryDate.getHistype() != null) {
            switch (queryDate.getHistype()) {
                case "all":
                    allHisEvent = alertMapper.getAllHisEvent(queryDate);
                    allComm = alertMapper.getAllCommStatus(queryDate);
                    break;
                case "area":
                    allHisEvent = alertMapper.getAreaHisEvent(queryDate);
                    allComm = alertMapper.getAreaCommStatus(queryDate);
                    break;
                case "sub":
                    allHisEvent = alertMapper.getSubHisEvent(queryDate);
                    allComm = alertMapper.getSubCommStatus(queryDate);
                    break;
                case "bay":
                    allHisEvent = alertMapper.getBayHisEvent(queryDate);
                    allComm = alertMapper.getBayCommStatus(queryDate);
                    break;
                case "ied":
                    allHisEvent = alertMapper.getIedHisEvent(queryDate);
                    allComm = alertMapper.getIedCommStatus(queryDate);
                    break;
            }
        }

        if (allHisEvent.size() > 0) {
            System.err.println("null");
            allHisEvent.forEach(he -> {
                int hmsms = he.getHmsms();
                int ymd = he.getYmd();
                StringBuilder fullTimeString = new StringBuilder();
                String eventTime;
                String dateString = String.valueOf(ymd);
                String timeString = String.valueOf(hmsms);

                eventTime = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6, 8) + " ";

                fullTimeString.append("0".repeat(Math.max(0, (9 - timeString.length()))));
                fullTimeString.append(timeString);

                eventTime += fullTimeString.substring(0, 2) + ":" + fullTimeString.substring(2, 4) + ":"
                        + fullTimeString.substring(4, 6) + "." + fullTimeString.substring(6, 8);
                he.setTime(eventTime);

            });
        }


        if (allComm.size() > 0) {
            allComm.forEach(co -> {
                int commDate = co.getConnectionTime();
                int disConnectionTime = co.getDisConnectionTime();
                String ct = formatTime(commDate);
                co.setConnTime(ct);
                String dct = formatTime(disConnectionTime);
                co.setDisConnTime(dct);
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(2);
                String result = numberFormat.format((float) commDate / (float) (commDate + disConnectionTime) * 100);
                Double connRate = Double.valueOf(result);
                co.setConnRate(connRate);

            });
        }

        map.put("hisEvent", allHisEvent);
        map.put("areas", areas);
        map.put("comm", allComm);
        return map;
    }

    @Override
    public List<IntelAlertLevel> getIntelAlertLevel(TunnelFilterEntity  intelFilter) {
        return alertMapper.getIntelAlertLevel(intelFilter);
    }

    @Override
    public List<DashboardReportVo> getJudgeReportStatistics() {
        return alertMapper.getJudgeReportStatistics();
    }

    @Override
    public String getVisitRate() {
        Integer number = baseMapper.getLatestVisitReportNumber();
        Integer substationNumber = baseMapper.getSubstationNumber();
        return String.format("%.2f", ((number.doubleValue() / substationNumber.doubleValue()) * 100)) + "%";
    }

    @Override
    public List<Tb1041Substation> getNotVisitStationList() {
            return baseMapper.getNotVisitStationList();
    }

    @Override
    public List<Tb1041Substation> getSubstationList() {
        QueryWrapper<Tb1041Substation> queryWrapper = new QueryWrapper<>();
        return substationMapper.selectList(queryWrapper);
    }

    String formatTime(int time) {
        int day = time / 60 / 60 / 24;
        int hour = time / 60 / 60 % 24;
        int minute = time / 60 % 60;
        int second = time % 60;
        String dt = day == 0 ? "" : day + "天";
        String ht = hour == 0 ? "" : hour + "时";
        String mt = minute == 0 ? "" : minute + "分";
        String st = second == 0 ? "" : second + "秒";
        String cd = dt + ht + mt + st;
        return cd;
    }
}
