package com.gridnt.minio.entity;

import lombok.Data;

@Data
public class UploadParams {

    /**
     * 业务层级（1运营商2园区3站所4设备）
     */
    private Integer level;

    /**
     * 业务路径
     */
    private String category;

    /**
     * 租户(运营商)id
     */
    private Long tenantId;

    /**
     * 园区id
     */
    private Long customerId;

    /**
     * 站所id
     */
    private Long substationId;


    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备模型id, 主要用于网关配置设备模型文件上传
     */
    private String modelUuid;

    /**
     * 是否需要在上传文件后，直接请求到其presignedUrl
     */
    private Boolean needUrl = false;

    /**
     * 是否需要在上传文件后覆盖原文件
     */
    private Boolean needCover = false;

    private String path;
}
