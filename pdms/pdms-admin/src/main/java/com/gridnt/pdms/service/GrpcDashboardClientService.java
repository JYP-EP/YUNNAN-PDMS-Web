package com.gridnt.pdms.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gridnt.common.web.ResponseDTO;
import com.gridnt.pdms.domain.SubstationMapVo;
import com.gridnt.pdms.domain.Tb1041Substation;
import com.gridnt.pdms.domain.TbCrc;
import com.gridnt.pdms.mapper.Tb1041SubstationMapper;
import com.gridnt.pdms.util.CommonUtil;
import com.pdms.grpc.domain.*;
import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@DataSource(value = DataSourceType.SLAVE)
public class GrpcDashboardClientService {

    @Value("${grpc.client.grpc-server.host}")
    private String host;

    @Value("${grpc.client.grpc-server.port}")
    private int port;

    private  SubStationStatusGrpc.SubStationStatusBlockingStub subStationStatusStub;

    private  VisitationGrpc.VisitationBlockingStub visitationBlockingStub;

    private  WarningGrpc.WarningBlockingStub warningBlockingStub;

    private DashBoardGrpc.DashBoardBlockingStub dashBoardBlockingStub;

    @Resource
    private Tb1041SubstationMapper substationMapper;

    @PostConstruct
    public void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        subStationStatusStub = SubStationStatusGrpc.newBlockingStub(managedChannel);
        visitationBlockingStub = VisitationGrpc.newBlockingStub(managedChannel);
        warningBlockingStub = WarningGrpc.newBlockingStub(managedChannel);
        dashBoardBlockingStub = DashBoardGrpc.newBlockingStub(managedChannel);
    }

    public String getGrpcData(Map<String,Object> map){
        String requestString = CommonUtil.mapConvertString(map);
        GrpcCommonRequest request = GrpcCommonRequest.newBuilder()
                .setRequest(requestString)
                .build();
        GrpcCommonReply boardGrpcData = dashBoardBlockingStub.getDashBoardGrpcData(request);
        return boardGrpcData.getReply();
    }

    public  Map<String,Object> getSubStationStatus(String substationId) {
        Map<String,Object> result = new HashMap<>();
        SubStationStatusRequest request = SubStationStatusRequest.newBuilder()
                .setSubstationId(substationId)
                .build();
        SubStationStatusReply subStationStatusReply = subStationStatusStub.getStatusStatistics(request);
        result.put("status",subStationStatusReply.getStatus());
        result.put("gooseRate",subStationStatusReply.getGooseRate());
        result.put("mmsRate",subStationStatusReply.getMmsRate());
        result.put("smvRate",subStationStatusReply.getSmvRate());
        result.put("onlineRate",subStationStatusReply.getOnlineRate());
        return result;
    }

    public  Map<String,Object> getAllStationStatusData() {
        Map<String,Object> result = new HashMap<>();
        AllStationStatusRequest request = AllStationStatusRequest.newBuilder()
                .build();
        AllStationStatusReply reply = subStationStatusStub.getAllStationStatusRequest(request);
        result.put("gooseRate",reply.getGooseRate());
        result.put("mmsRate",reply.getMmsRate());
        result.put("smvRate",reply.getSmvRate());
        result.put("onlineRate",reply.getOnlineRate());
        return result;
    }

    public  Map<String,Object> getVisitationStatistics(String substationId){
        Map<String,Object> result = new HashMap<>();
        VisitationRequest request = VisitationRequest.newBuilder().setSubstationId(substationId)
                .build();
        VisitationReply reply = visitationBlockingStub.getVisitationStatistics(request);
        result.put("visitationRate",reply.getVisitationRate());
        return result;
    }

    public Map<String,Object> getWarningStatistics(String substationId){
        Map<String,Object> result = new HashMap<>();
        WarningRequest warningRequest = WarningRequest.newBuilder().setSubstationId(substationId).build();
        WarningAndLockReply reply = warningBlockingStub.getWarningStatistics(warningRequest);
        result.put("lockNumber",reply.getLockNumber());
        result.put("warningNumber",reply.getWarningNumber());
        result.put("procStatusNumber",reply.getProcStatusNumber());
        return result;
    }

    public List<Map<String,Object>> getWarningStatisticsList(){

        List<Map<String,Object>> result = new ArrayList<>();
        try {
            WarningListRequest request = WarningListRequest.newBuilder().build();
            WarningListReply substationWarningList = warningBlockingStub.getSubstationWarningList(request);
            List<WarningItemReply> replyList = substationWarningList.getReplyList();
            replyList.forEach(reply -> {
                Map<String, Object> map = new HashMap<>();
                map.put("substationName", reply.getStationName());
                map.put("warningNumber", reply.getWarningNumber());
                map.put("type", reply.getType());
                result.add(map);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public List<Tb1041Substation> getSubstationListForMap() {
        List<Tb1041Substation> tb1041Substations = substationMapper.selectList(Wrappers.emptyWrapper());
        return tb1041Substations;
    }

    public Map<String,SubstationMapVo> getSubstationStatusForMap() {
        QueryWrapper<Tb1041Substation> queryWrapper = new QueryWrapper<>();
        List<Tb1041Substation> tb1041Substations = substationMapper.selectList(queryWrapper);
        //数据重新组装
        Map<String,SubstationMapVo> result = getSubsStatusMap(tb1041Substations);
        return  result;
    }

    public Integer getWarningAllCount() {
        Integer result = null;
        try {
            result = 0;
            WarningListRequest request = WarningListRequest.newBuilder().build();
            WarningListReply substationWarningList = warningBlockingStub.getSubstationWarningList(request);
            List<WarningItemReply> replyList = substationWarningList.getReplyList();
            for (int i = 0; i < replyList.size(); i++) {
                WarningItemReply reply = replyList.get(i);
                String number = reply.getWarningNumber();
                if (ObjectUtils.isNotEmpty(number)) {
                    result += Integer.valueOf(number);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String,SubstationMapVo> getSubstationStatusByDq(Map<String, Object> params) {
        QueryWrapper<Tb1041Substation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("F1041_DQDESC",params.get("dq"));
        List<Tb1041Substation> tb1041Substations = substationMapper.selectList(queryWrapper);
        return getSubsStatusMap(tb1041Substations);
    }

    private Map<String,SubstationMapVo> getSubsStatusMap(List<Tb1041Substation> tb1041Substations){
        Map<String,SubstationMapVo> result = new HashMap<>();
        //数据重新组装
        for (int i = 0; i < tb1041Substations.size(); i++) {
            Tb1041Substation tb1041 = tb1041Substations.get(i);
            SubstationMapVo mapVo = new SubstationMapVo();
            result.put(tb1041.getF1041Code(),mapVo);
            try {
                Map<String, Object> stationStatus = getSubStationStatus(tb1041.getF1041Code());
                mapVo.setGooseRate((String) stationStatus.get("gooseRate"));
                mapVo.setSmvRate((String) stationStatus.get("smvRate"));
                mapVo.setOnlineRate((String) stationStatus.get("onlineRate"));
                mapVo.setMmsRate((String) stationStatus.get("mmsRate"));
                mapVo.setStatus((String) stationStatus.get("status"));
                Map<String, Object> warningStatistics = getWarningStatistics(tb1041.getF1041Code());
                mapVo.setLockNumber((String) warningStatistics.get("lockNumber"));
                mapVo.setWarningNumber((String) warningStatistics.get("warningNumber"));
                mapVo.setProcStatusNumber((String) warningStatistics.get("procStatusNumber"));
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
        return result;
    }
}
