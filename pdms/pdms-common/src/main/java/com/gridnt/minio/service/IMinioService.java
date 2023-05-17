package com.gridnt.minio.service;

import com.gridnt.minio.entity.FileVo;
import com.gridnt.minio.entity.UploadParams;
import com.gridnt.minio.entity.UploadResult;
import io.minio.ObjectStat;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * minio访问服务类
 *
 * @author canyongz
 */
public interface IMinioService {

    String BUCKET_NAME = "iotoss";

    UploadResult uploadFile(MultipartFile file, UploadParams uploadParams) throws Exception;

    /**
     * 上传cid文件
     *
     * @param tenantSchemaName
     * @param substationCode
     * @param iedName
     * @param file
     * @throws Exception
     */
    void uploadCid(String tenantSchemaName, String substationCode, String iedName, File file) throws Exception;

    /**
     * remove cid
     *
     * @throws Exception
     */
    void removeFile(String fileName) throws Exception;

    /**
     * 取 svg
     *
     * @param fileName
     * @throws Exception
     */
    String getFileUrl(String fileName) throws Exception;


    InputStream downLoadFile(String fileName) throws Exception;

    void uploadCid(String tenantName, String substationCode, String iedName, InputStream inputStream) throws Exception;

    void putFile(String objectName, String filePath) throws Exception;

    void getFile(String objectName, String filePath) throws Exception;

    ObjectStat statFile(String objectName);

    String getUrl(String objectName);

    void uploadFile(String fileName, File file, String category) throws Exception;

    void uploadFile(String fileName, InputStream inputStream, String bucketName) throws Exception;

    String getPresignedObjectUrl(String fileName) throws Exception;

    String removeFile(String bucketName, String fileName);

    void uploadChangePath(String oldPath, String newPath);

    void downloadFiles(String filename, HttpServletResponse httpResponse);

    List<FileVo> fileList() throws Exception;

    List<FileVo> fileList(String prefix) throws Exception;
    void downloadPackages(String downLoadPath, HttpServletResponse resp) throws  Exception;

    boolean bucketExists(String mybucket) throws InvalidPortException, InvalidEndpointException;
}
