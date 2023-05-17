package com.gridnt.pdms.controller;

import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.WebUtils;
import com.gridnt.minio.entity.FileVo;
import com.gridnt.minio.service.IMinioService;
import com.gridnt.minio.service.impl.MinioServiceImpl;
import com.gridnt.pdms.constants.RecordModel;
import com.gridnt.pdms.domain.*;
import com.gridnt.pdms.service.ITb1046IedService;
import com.gridnt.pdms.service.MonitorService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.util.FileCopyUtils;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pdms/{version}/basic")
public class CommonController {
	@Autowired
	private ITb1046IedService iedService;

	@Autowired
	private MonitorService monitorService;
	@Autowired
	IMinioService minioService;

	@Value("${gridnt.minio.buckets}")
	private String bucketName;

	@GetMapping("/getDeviceTree")
	public ResponseDTO<List<DivisionNode>> getDeviceTree(@PathVariable("version") String version) {
		return WebUtils.createSuccessResponse(iedService.getDeviceTree());
	}

	@GetMapping("/getAnalogInfo/{deviceID}")
	public ResponseDTO<List<MonitorFCDA>> getAnalogInfo(@PathVariable("version") String version, @PathVariable("deviceID") String deviceID) {
		return WebUtils.createSuccessResponse(monitorService.getAnalogInfo(deviceID));
	}

	@GetMapping("/getConditionInfo/{deviceID}")
	public ResponseDTO<List<MonitorFCDA>> getConditionInfo(@PathVariable("version") String version, @PathVariable("deviceID") String deviceID) {
		return WebUtils.createSuccessResponse(monitorService.getConditionInfo(deviceID));
	}

	@GetMapping("/getStatusInfo/{deviceID}")
	public ResponseDTO<List<MonitorFCDA>> getStatusInfo(@PathVariable("version") String version, @PathVariable("deviceID") String deviceID) {
		return WebUtils.createSuccessResponse(monitorService.getStatusInfo(deviceID));
	}

	@GetMapping("/getWarningInfo/{deviceID}")
	public ResponseDTO<List<MonitorFCDA>> getWarningInfo(@PathVariable("version") String version, @PathVariable("deviceID") String deviceID) {
		return WebUtils.createSuccessResponse(monitorService.getWarningInfo(deviceID));
	}

	@GetMapping("/getOtherStatusInfo/{deviceID}")
	public ResponseDTO<List<MonitorFCDA>> getOtherStatusInfo(@PathVariable("version") String version, @PathVariable("deviceID") String deviceID) {
		return WebUtils.createSuccessResponse(monitorService.getOtherStatusInfo(deviceID));
	}

	@GetMapping("/getStripeInfo/{deviceID}")
	public ResponseDTO<List<MonitorFCDA>> getStripeInfo(@PathVariable("version") String version, @PathVariable("deviceID") String deviceID) {
		return WebUtils.createSuccessResponse(monitorService.getStripeInfo(deviceID));
	}

	@GetMapping("/getSettingInfo/{deviceID}")
	public ResponseDTO<List<SettingFCDA>> getSettingInfo(@PathVariable("version") String version, @PathVariable("deviceID") String deviceID) {
		return WebUtils.createSuccessResponse(monitorService.getSettingInfo(deviceID));
	}

	@GetMapping("/getParameterInfo/{deviceID}")
	public ResponseDTO<List<SettingFCDA>> getParameterInfo(@PathVariable("version") String version, @PathVariable("deviceID") String deviceID) {
		return WebUtils.createSuccessResponse(monitorService.getParameterInfo(deviceID));
	}

	@SneakyThrows
	@GetMapping("/getRecordList/{deviceID}")
	public ResponseDTO<List<RecordModel>> getRecordList(@PathVariable("version") String version, @PathVariable("deviceID") String deviceID) {
		List<RecordModel> recordList = new ArrayList<>();

		String basePath = System.getenv("HOME");
		//String basePath = "D:\\日志\\2023\\工作日志\\三月\\09";
		Tb1046Ied ied = iedService.getByCode(deviceID);
		Tb1041Substation substation = iedService.getSubstationByDeviceCode(deviceID);
		if(ied == null || substation == null) {
			return WebUtils.createSuccessResponse(recordList);
		}

		String directory = basePath + "/rs5000/data/" + substation.getF1041Name() + "/COMTRADE/" + ied.getF1046Name();
		String nimioPath = "rs5000/data/" + substation.getF1041Name() + "/COMTRADE/" + ied.getF1046Name();

		List<FileVo> Isfile = minioService.fileList(nimioPath);

		System.out.println(directory);
		File targetDir = new File(directory);
		if(!targetDir.exists()) {
			return WebUtils.createSuccessResponse(recordList);
		}
		File[] files = targetDir.listFiles();
		if(files == null) {
			return WebUtils.createSuccessResponse(recordList);
		}

		for(File file : files) {
			if(file.isFile()) {
				try {
					if(Isfile.stream().filter(x->x.getFileName().equals(nimioPath + "/" + file.getName())).count() == 0) {
						minioService.uploadFile(nimioPath + "/" + file.getName(), file, bucketName);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				recordList.add(new RecordModel(file.getName().split("\\.")[0],file.getName(),minioService.getFileUrl(nimioPath + "/" + file.getName())));
			}
		}

		return WebUtils.createSuccessResponse(recordList);
	}
}
