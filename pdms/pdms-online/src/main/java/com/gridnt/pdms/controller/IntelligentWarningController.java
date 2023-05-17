package com.gridnt.pdms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gridnt.common.web.ResultEnum;
import com.gridnt.common.web.WebUtils;
import com.gridnt.common.web.annotation.MultiRequestBody;
import com.gridnt.common.web.ResponseDTO;
import com.gridnt.pdms.domain.*;
import com.gridnt.pdms.service.ITbIntelligentWarningService;
import com.gridnt.pdms.xml.*;
import com.ruoyi.common.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.gridnt.pdms.util.*;

@RestController
@RequestMapping("/pdms/{version}/intel")
public class IntelligentWarningController extends BaseController {
    @Autowired
    private ITbIntelligentWarningService intelAlertService;

    @PostMapping("getByPage")
    public ResponseDTO<IPage<IntelAlertEntity>> getByPage(@PathVariable("version") String version,
                                                          @MultiRequestBody("page") Page<IntelAlertEntity> page,
                                                          @MultiRequestBody("entity") IntelFilterEntity intelFilter) {
        System.out.println(intelFilter.getStartTime() + " to " + intelFilter.getEndTime());
        return WebUtils.createSuccessResponse(intelAlertService.getIntelAlert(page, intelFilter));
    }

    @PostMapping("getStat")
    public ResponseDTO<List<IntelAlertStat>> getStat(@PathVariable("version") String version,
                                                     @MultiRequestBody("entity") IntelFilterEntity intelFilter) {
        List<IntelAlertStat> alertStats = intelAlertService.getIntelAlertStat(intelFilter);
        return WebUtils.createSuccessResponse(alertStats);
    }

    @PostMapping("frequentAlert")
    public ResponseDTO<IPage<FrequentAlertEntity>> getFrequentAlert(@PathVariable("version") String version,
                                                                    @MultiRequestBody("page") Page<FrequentAlertEntity> page,
                                                                    @MultiRequestBody("entity") IntelFilterEntity intelFilter) {
        return WebUtils.createSuccessResponse(intelAlertService.getFrequentAlert(page, intelFilter));
    }

    @PostMapping("frequentStat")
    public ResponseDTO<List<FrequentStat>> getFrequentStat(@PathVariable("version") String version,
                                                           @MultiRequestBody("entity") IntelFilterEntity intelFilter) {
        List<FrequentStat> frequentStats = intelAlertService.getFrequentStat(intelFilter);
        return WebUtils.createSuccessResponse(frequentStats);
    }

    @PostMapping("tunnelAlert")
    public ResponseDTO<IPage<ProtectionTunnelAlertEntity>> getProtectionTunnelAlert(@PathVariable("version") String version,
                                                                                    @MultiRequestBody("page") Page<ProtectionTunnelAlertEntity> page,
                                                                                    @MultiRequestBody("entity") TunnelFilterEntity intelFilter) {
        return WebUtils.createSuccessResponse(intelAlertService.getProtectionTunnelAlert(page, intelFilter));
    }

    @PostMapping("blockingAlert")
    public ResponseDTO<IPage<BlockingAlertEntity>> getBlockingAlert(@PathVariable("version") String version,
                                                                    @MultiRequestBody("page") Page<BlockingAlertEntity> page,
                                                                    @MultiRequestBody("entity") TunnelFilterEntity intelFilter) {
        return WebUtils.createSuccessResponse(intelAlertService.getBlockingAlert(page, intelFilter));
    }

    @PostMapping("blockingStat")
    public ResponseDTO<List<BlockingStat>> getBlockingStat(@PathVariable("version") String version,
                                                           @MultiRequestBody("entity") TunnelFilterEntity intelFilter) {
        List<BlockingStat> blockingStats = intelAlertService.getBlockingStat(intelFilter);
        return WebUtils.createSuccessResponse(blockingStats);
    }

    @PostMapping("inspectionAlert")
    public ResponseDTO<IPage<IntelAlertEntity>> getInspectionAlert(@PathVariable("version") String version,
                                                                   @MultiRequestBody("page") Page<IntelAlertEntity> page,
                                                                   @MultiRequestBody("entity") IntelFilterEntity intelFilter) {
        intelFilter.setAlertType(InspectionReportXml.ID);
        return WebUtils.createSuccessResponse(intelAlertService.getIntelAlert(page, intelFilter));
    }

    @PostMapping("checkAlert")
    public ResponseDTO<IPage<IntelAlertEntity>> getCheckAlert(@PathVariable("version") String version,
                                                              @MultiRequestBody("page") Page<IntelAlertEntity> page,
                                                              @MultiRequestBody("entity") IntelFilterEntity intelFilter) {
        intelFilter.setAlertType(CheckReportXml.ID);
        return WebUtils.createSuccessResponse(intelAlertService.getIntelAlert(page, intelFilter));
    }

    @PostMapping("statAlert")
    public ResponseDTO<IPage<IntelAlertEntity>> getStatAlert(@PathVariable("version") String version,
                                                             @MultiRequestBody("page") Page<IntelAlertEntity> page,
                                                             @MultiRequestBody("entity") IntelFilterEntity intelFilter) {
        intelFilter.setAlertType(CommunicationRatioXml.ID);
        return WebUtils.createSuccessResponse(intelAlertService.getIntelAlert(page, intelFilter));
    }

    @GetMapping("detail/frequent")
    public ResponseDTO<FrequentAlertXml> getFrequentAlertDetail(@PathVariable("version") String version,
                                                                @RequestParam("fileId") String fileId,
                                                                @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case FrequentAlertEntity.ID: // frequentAlert
                case FrequentAlertEntity.AliasID: // frequentAlert
                    FrequentAlertXml result = xmlMapper.readValue(fileContext.getContext(),
                            FrequentAlertXml.class);
                    return WebUtils.createSuccessResponse(result);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("detail/tunnel")
    public ResponseDTO<TunnelAlertXml> getTunnelAlertDetail(@PathVariable("version") String version,
                                                            @RequestParam("fileId") String fileId,
                                                            @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case ProtectionTunnelAlertEntity.ID:
                    TunnelAlertXml tunnelAlertXml = xmlMapper.readValue(fileContext.getContext(),
                            TunnelAlertXml.class);
                    return WebUtils.createSuccessResponse(tunnelAlertXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    /**
     * 区外分析-xml
     */
    @GetMapping("detail/outrim")
    public ResponseDTO<OutRimAlertXML> getOutRimAlertDetail(@PathVariable("version") String version,
                                                            @RequestParam("fileId") String fileId,
                                                            @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case OutRimAlertXML.ID:
                    String context = fileContext.getContext();
                    OutRimAlertXML outRimAlertXML = xmlMapper.readValue(context,
                            OutRimAlertXML.class);
                    ResponseDTO<OutRimAlertXML> successResponse = WebUtils.createSuccessResponse(outRimAlertXML);
                    System.out.println(context);
                    return successResponse;
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    /**
     * 区外分析-告警列表
     */
    @PostMapping("outRimAlert")
    public ResponseDTO<IPage<OutRimAlertEntity>> getOutRimAlert(@PathVariable("version") String version,
                                                                @MultiRequestBody("page") Page<OutRimAlertEntity> page,
                                                                @MultiRequestBody("entity") IntelFilterEntity intelFilter) {
        return WebUtils.createSuccessResponse(intelAlertService.getOutRimAlert(page, intelFilter));
    }

    /**
     * 主变变中-xml
     */
    @GetMapping("detail/transformer")
    public ResponseDTO<TransformerCurrentXML> getTransformerCurrentDetail(@PathVariable("version") String version,
                                                                          @RequestParam("fileId") String fileId,
                                                                          @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case TransformerCurrentXML.ID:
                    String context = fileContext.getContext();
                    TransformerCurrentXML transformerCurrentXML = xmlMapper.readValue(context, TransformerCurrentXML.class);
                    ResponseDTO<TransformerCurrentXML> successResponse = WebUtils.createSuccessResponse(transformerCurrentXML);
                    System.out.println(context);
                    return successResponse;
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    /**
     * 主变变中-告警列表
     */
    @PostMapping("transformerAlert")
    public ResponseDTO<IPage<TransformerCurrentEntity>> getTransformerCurrentAlert(@PathVariable("version") String version,
                                                                                   @MultiRequestBody("page") Page<TransformerCurrentEntity> page,
                                                                                   @MultiRequestBody("entity") IntelFilterEntity intelFilter) {
        return WebUtils.createSuccessResponse(intelAlertService.getTransformerCurrentAlert(page, intelFilter));
    }

    @GetMapping("detail/stat01")
    public ResponseDTO<CommunicationRatioXml> getSTAT01Detail(@PathVariable("version") String version,
                                                              @RequestParam("fileId") String fileId,
                                                              @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case CommunicationRatioXml.ID:
                    CommunicationRatioXml statXml = xmlMapper.readValue(fileContext.getContext(),
                            CommunicationRatioXml.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("detail/stat03")
    public ResponseDTO<SecCircuitXml> getSTAT03Detail(@PathVariable("version") String version,
                                                      @RequestParam("fileId") String fileId,
                                                      @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case SecCircuitXml.ID:
                    SecCircuitXml statXml = xmlMapper.readValue(fileContext.getContext(),
                            SecCircuitXml.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("detail/stat03_G")
    public ResponseDTO<G_SecCircuit> getSTAT03_GDetail(@PathVariable("version") String version,
                                                      @RequestParam("fileId") String fileId,
                                                      @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case G_SecCircuit.ID:
                    G_SecCircuit statXml = xmlMapper.readValue(fileContext.getContext(),
                            G_SecCircuit.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("detail/G/stat06")
    public ResponseDTO<G_SettingValueAlert> getSTAT06_GDetail(@PathVariable("version") String version,
                                                       @RequestParam("fileId") String fileId,
                                                       @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case G_SettingValueAlert.ID:
                    G_SettingValueAlert statXml = xmlMapper.readValue(fileContext.getContext(),
                            G_SettingValueAlert.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("detail/stat04")
    public ResponseDTO<AnalogXml> getSTAT04Detail(@PathVariable("version") String version,
                                                  @RequestParam("fileId") String fileId,
                                                  @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case AnalogXml.ID:
                    AnalogXml statXml = xmlMapper.readValue(fileContext.getContext(),
                            AnalogXml.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("detail/G/stat04")
    public ResponseDTO<G_AnalogXml> getSTAT04_GDetail(@PathVariable("version") String version,
                                                  @RequestParam("fileId") String fileId,
                                                  @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case G_AnalogXml.ID:
                    G_AnalogXml statXml = xmlMapper.readValue(fileContext.getContext(),
                            G_AnalogXml.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("detail/stat05")
    public ResponseDTO<DoubleAnalogXml> getSTAT05Detail(@PathVariable("version") String version,
                                                        @RequestParam("fileId") String fileId,
                                                        @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case DoubleAnalogXml.ID:
                    DoubleAnalogXml statXml = xmlMapper.readValue(fileContext.getContext(),
                            DoubleAnalogXml.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("detail/stat06")
    public ResponseDTO<CompareXml> getSTAT06Detail(@PathVariable("version") String version,
                                                   @RequestParam("fileId") String fileId,
                                                   @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case CompareXml.ID:
                    CompareXml statXml = xmlMapper.readValue(fileContext.getContext(),
                            CompareXml.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    @GetMapping("detail/checkReport")
    public ResponseDTO<CheckReportXml> getCheckReportDetail(@PathVariable("version") String version,
                                                            @RequestParam("fileId") String fileId,
                                                            @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case CheckReportXml.ID:
                    CheckReportXml statXml = xmlMapper.readValue(fileContext.getContext(),
                            CheckReportXml.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }


    @GetMapping("detail/inspection")
    public ResponseDTO<InspectionReportXml> getInspectionReportDetail(@PathVariable("version") String version,
                                                                      @RequestParam("fileId") String fileId,
                                                                      @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case InspectionReportXml.ID:
                    InspectionReportXml statXml = xmlMapper.readValue(fileContext.getContext(),
                            InspectionReportXml.class);
                    return WebUtils.createSuccessResponse(statXml);
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }
    //二次回路故障诊断-消失
    @GetMapping("detail/SecCircuitFaultReport")
    public ResponseDTO<SecCircuitFaultDiagXml> getSecCircuitFaultReportDetail(@PathVariable("version") String version,
                                                            @RequestParam("fileId") String fileId,
                                                            @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);

        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case SecCircuitFaultDiagXml.ID:
                    String Xmltext = fileContext.getContext();
                    if(!Xmltext.isEmpty()) {
                        SecCircuitFaultDiagXml statXml = xmlMapper.readValue(Xmltext,
                                SecCircuitFaultDiagXml.class);
                        return WebUtils.createSuccessResponse(statXml);
                    }
                    else {
                        return WebUtils.createResponse(ResultEnum.ERROR, "this Alarm file Context is null~");
                    }
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }
    //状态监测预警
    @GetMapping("detail/StatusMonitorReport")
    public ResponseDTO<StatusMonitorXml> getStatusMonitorReportDetail(@PathVariable("version") String version,
                                                                              @RequestParam("fileId") String fileId,
                                                                              @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);

        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case StatusMonitorXml.ID:
                    String Xmltext = fileContext.getContext();
                    if(!Xmltext.isEmpty()) {
                        StatusMonitorXml statXml = xmlMapper.readValue(Xmltext,
                                StatusMonitorXml.class);
                        return WebUtils.createSuccessResponse(statXml);
                    }
                    else {
                        return WebUtils.createResponse(ResultEnum.ERROR, "this Alarm file Context is null~");
                    }
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    //同源数据比对
    @GetMapping("detail/SamCompReport")
    public ResponseDTO<SamCompXml> getSamCompReportDetail(@PathVariable("version") String version,
                                                                      @RequestParam("fileId") String fileId,
                                                                      @RequestParam("type") Integer type) {
        System.out.println("fileID: " + fileId + " type:" + type);

        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case SamCompXml.ID:
                    String Xmltext = fileContext.getContext();
                    if(!Xmltext.isEmpty()) {
                        SamCompXml statXml = xmlMapper.readValue(Xmltext,
                                SamCompXml.class);
                        return WebUtils.createSuccessResponse(statXml);
                    }
                    else {
                        return WebUtils.createResponse(ResultEnum.ERROR, "this Alarm file Context is null~");
                    }
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }


    //定值和版本校核-消失
    @GetMapping("detail/SettingReport")
    public ResponseDTO<G_SettingValueAlert> getSettingReportDetail(@PathVariable("version") String version,
                                                                   @RequestParam("fileId") String fileId,
                                                                   @RequestParam("type") Integer type){
        System.out.println("fileID: " + fileId + " type:" + type);

        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case G_SettingValueAlert.ID:
                    String Xmltext = fileContext.getContext();

                    //fileContext.getContext();
                    if(!Xmltext.isEmpty()) {
                        G_SettingValueAlert statXml = xmlMapper.readValue(Xmltext,
                                G_SettingValueAlert.class);
                        return WebUtils.createSuccessResponse(statXml);
                    }
                    else {
                        return WebUtils.createResponse(ResultEnum.ERROR, "this Alarm file Context is null");
                    }
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }


    //装置异常告警定位
    @GetMapping("detail/DevAlaLocationReport")
    public ResponseDTO<DevAlaLocationXml> getDevAlaLocationReportDetail(@PathVariable("version") String version,
                                                                   @RequestParam("fileId") String fileId,
                                                                   @RequestParam("type") Integer type){
        System.out.println("fileID: " + fileId + " type:" + type);

        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case DevAlaLocationXml.ID:
                    String Xmltext = fileContext.getContext();

                    //fileContext.getContext();
                    if(!Xmltext.isEmpty()) {
                        DevAlaLocationXml statXml = xmlMapper.readValue(Xmltext,
                                DevAlaLocationXml.class);
                        return WebUtils.createSuccessResponse(statXml);
                    }
                    else {
                        return WebUtils.createResponse(ResultEnum.ERROR, "this Alarm file Context is null");
                    }
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    //跳合闸返校诊断
    @GetMapping("detail/TripDiagReport")
    public ResponseDTO<TripDiagXml> getTripDiagXmlReportDetail(@PathVariable("version") String version,
                                                                        @RequestParam("fileId") String fileId,
                                                                        @RequestParam("type") Integer type){
        System.out.println("fileID: " + fileId + " type:" + type);

        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case TripDiagXml.ID:
                    String Xmltext = fileContext.getContext();

                    //fileContext.getContext();
                    if(!Xmltext.isEmpty()) {
                        TripDiagXml statXml = xmlMapper.readValue(Xmltext,
                                TripDiagXml.class);
                        return WebUtils.createSuccessResponse(statXml);
                    }
                    else {
                        return WebUtils.createResponse(ResultEnum.ERROR, "this Alarm file Context is null");
                    }
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }

    //保护闭锁告警信息-消失
    @GetMapping("detail/RelayBlkInfoReport")
    public ResponseDTO<RelayBlkInfoXml> getRelayBlkInfoReportDetail(@PathVariable("version") String version,
                                                                        @RequestParam("fileId") String fileId,
                                                                        @RequestParam("type") Integer type){
        System.out.println("fileID: " + fileId + " type:" + type);

        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }

        XmlMapper xmlMapper = new XmlMapper();
        try {
            switch (type) {
                case RelayBlkInfoXml.ID:
                    String Xmltext = fileContext.getContext();

                    //fileContext.getContext();
                    if(!Xmltext.isEmpty()) {
                        RelayBlkInfoXml statXml = xmlMapper.readValue(Xmltext,
                                RelayBlkInfoXml.class);
                        return WebUtils.createSuccessResponse(statXml);
                    }
                    else {
                        return WebUtils.createResponse(ResultEnum.ERROR, "this Alarm file Context is null");
                    }
                default:
                    return WebUtils.createResponse(ResultEnum.ERROR, "cannot recognize alert type: " + type);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebUtils.createResponse(ResultEnum.ERROR, e.getMessage());
        }
    }


    @GetMapping("fileRawContent")
    public ResponseDTO<FileContextEntity> getFileRawContent(@PathVariable("version") String version,
                                                            @RequestParam("fileId") String fileId) {
        FileContextEntity fileContext = intelAlertService.getFileContext(fileId);
        if (fileContext == null) {
            return WebUtils.createResponse(ResultEnum.ERROR, "cannot find the specified file");
        }
        return WebUtils.createSuccessResponse(fileContext);
    }

    // 变电站/间隔/装置 层级关系
    @GetMapping("division")
    public ResponseDTO<List<DivisionNode>> getSubstationDivision(@PathVariable("version") String version) {
        return WebUtils.createSuccessResponse(intelAlertService.getDivision());
    }

    //获取通信状态
//    @PostMapping("commStatus")
//    public ResponseDTO<Map<String, Object>> getCommStat(
//            @RequestBody CommSta_queryDate queryDate) {
    @PostMapping("commStatus")
    public ResponseDTO<Map<String, Object>> getCommStat(@RequestBody CommSta_queryDate queryDate) {
        return WebUtils.createSuccessResponse(intelAlertService.getCommStat(queryDate));
    }

    @PostMapping("getLevel")
    public ResponseDTO<List<IntelAlertLevel>> getLevel(@PathVariable("version") String version,
                                                     @MultiRequestBody("entity") TunnelFilterEntity intelFilter) {
        List<IntelAlertLevel> alertLevel = intelAlertService.getIntelAlertLevel(intelFilter);
        return WebUtils.createSuccessResponse(alertLevel);
    }

    @GetMapping("getJudgeReportStatistics")
    public ResponseDTO<List<DashboardReportVo>> getJudgeReportStatistics(@PathVariable("version") String version) {
        List<DashboardReportVo> alertLevel = intelAlertService.getJudgeReportStatistics();
        return WebUtils.createSuccessResponse(alertLevel);
    }
    @GetMapping("getVisitRate")
    public ResponseDTO<String> getVisitRate(@PathVariable("version") String version) {
        String visitRate = intelAlertService.getVisitRate();
        return WebUtils.createSuccessResponse(visitRate);
    }

    @GetMapping("getNotVisitStationList")
    public ResponseDTO<List<Tb1041Substation>> getNotVisitStationList(@PathVariable("version") String version) {
        List<Tb1041Substation> list = intelAlertService.getNotVisitStationList();
        return WebUtils.createSuccessResponse(list);
    }

    @GetMapping("getSubstationList")
    public ResponseDTO<List<Tb1041Substation>> getSubstationList(@PathVariable("version") String version) {
        List<Tb1041Substation> list = intelAlertService.getSubstationList();
        return WebUtils.createSuccessResponse(list);
    }




}
