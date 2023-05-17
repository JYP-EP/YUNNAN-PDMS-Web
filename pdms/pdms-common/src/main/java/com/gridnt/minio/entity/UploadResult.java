package com.gridnt.minio.entity;

import lombok.Data;

@Data
public class UploadResult {
    /**
     * minio中自bucketname之后的，文件串名
     */
    private String fileUrl;
    /**
     * 获得的可访问的url地址
     */
    private String presignedUrl;
}
