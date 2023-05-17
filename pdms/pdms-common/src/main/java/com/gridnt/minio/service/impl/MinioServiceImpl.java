package com.gridnt.minio.service.impl;

import com.gridnt.minio.entity.FileVo;
import com.gridnt.minio.entity.UploadParams;
import com.gridnt.minio.entity.UploadResult;
import com.gridnt.minio.service.IMinioService;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * 服务类
 *
 * @author canyongz
 */
@Slf4j
@Service
public class MinioServiceImpl implements IMinioService {

    @Value("${gridnt.minio.url}")
    private String url;

    @Value("${gridnt.minio.userName}")
    private String userName;

    @Value("${gridnt.minio.password}")
    private String password;

    @Value("${gridnt.minio.buckets}")
    private String bucketName;// = "iotoss"


    @PostConstruct
    public void init() {
        try {
            createBucket(getClient(), bucketName);
        } catch (Exception e) {
            log.error("error when init MinioServiceImpl, ", e);
        }
    }

    @Override
    public UploadResult uploadFile(MultipartFile file, UploadParams uploadParams) throws Exception {
        String path = uploadParams.getPath();
        String fileName = file.getOriginalFilename();
        String fileType = fileName.substring(fileName.indexOf(".") + 1);
        String originName = fileName.substring(0, fileName.indexOf("."));
        String finalName = "";
        if (!uploadParams.getNeedCover()) {
            finalName = originName + UUID.randomUUID().toString().substring(0, 12) + "." + fileType;
        } else {
            finalName = originName + "." + fileType;
        }
        String fileNewName = path + "/" + finalName;
        // minioService.uploadFile(fileNewName, file.getInputStream(), BUCKET_NAME);
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + File.separator + fileNewName);
        if (!tmpFile.getParentFile().exists()) {
            tmpFile.getParentFile().mkdirs();
        }
        file.transferTo(tmpFile);
        this.putFile(fileNewName, tmpFile.getAbsolutePath());
        tmpFile.delete();
        UploadResult uploadResult = new UploadResult();
        if (uploadParams.getNeedUrl()) {
            String presignedObjectUrl = this.getPresignedObjectUrl(fileNewName);
            uploadResult.setPresignedUrl(presignedObjectUrl);
        }
        uploadResult.setFileUrl(fileNewName);
        return uploadResult;
    }


    private MinioClient getClient() throws InvalidPortException, InvalidEndpointException {
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioClient minioClient = new MinioClient(url, userName, password);
        return minioClient;
    }

    private void createBucket(MinioClient minioClient, String bucketName) throws Exception {
        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(bucketName);
        if (isExist) {
        } else {
            // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
            minioClient.makeBucket(bucketName);
        }
    }

    @Override
    public void uploadCid(String tenantSchemaName, String substationCode, String iedName, File file) throws Exception {
        try {
            String fileName = String.format("%s/%s/%s/%s.cid", tenantSchemaName, substationCode, "cid", iedName);
            MinioClient client = getClient();
            InputStream inputStream = null;
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            }
            client.putObject(bucketName, fileName, inputStream, new PutObjectOptions(file.length(), -1));
        } catch (Exception e) {
            log.error("error when upload cid, ", e);
            throw e;
        }
    }

    @Override
    public void uploadCid(String tenantSchemaName, String substationCode, String iedName, InputStream inputStream) throws Exception {
        try {
            String fileName = String.format("%s/%s/%s/%s.cid", tenantSchemaName, substationCode, "cid", iedName);
            MinioClient client = getClient();
            client.putObject(bucketName, fileName, inputStream, new PutObjectOptions(inputStream.available(), -1));
        } catch (Exception e) {
            log.error("error when upload cid, ", e);
            throw e;
        }
    }

    @Override
    public void uploadFile(String fileName, InputStream inputStream, String category) throws Exception {
        try {
            MinioClient client = getClient();
            //创建bucket
            createBucket(client, category);
            client.putObject(category, fileName, inputStream, new PutObjectOptions(inputStream.available(), -1));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error when upload file, ", e);
            throw e;
        }
    }

    /**
     * 变换路径
     *
     * @param oldPath
     * @param newPath
     */
    @Override
    public void uploadChangePath(String oldPath, String newPath) {
        try {
//            InputStream ins = downLoadFile(oldPath);
//            getClient().putObject(bucketName,newPath,ins,new PutObjectOptions(ins.available(),-1));
            getClient().copyObject(bucketName, newPath, null, null, bucketName, oldPath, null, null);
        } catch (Exception e) {
            log.error("error when change path,", e);
        }
    }

    @Override
    public void downloadFiles(String filename, HttpServletResponse httpResponse) {
        try {
            InputStream object = getClient().getObject(bucketName, filename);
            byte buf[] = new byte[1024];
            int length = 0;
            httpResponse.reset();
            httpResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            httpResponse.setContentType("application/octet-stream");
            httpResponse.setCharacterEncoding("utf-8");
            OutputStream outputStream = httpResponse.getOutputStream();
            while ((length = object.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            outputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void uploadFile(String fileName, File file, String category) throws Exception {
        try {
            MinioClient client = getClient();
            //创建bucket
            createBucket(client, category);
            InputStream inputStream = null;
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            }
            client.putObject(category, fileName, inputStream, new PutObjectOptions(file.length(), -1));
        } catch (Exception e) {
            log.error("error when upload file, ", e);
            throw e;
        }
    }

    /**
     * 删除minio上的文件
     *
     * @param bucketName
     * @param fileName
     */
    @Override
    public String removeFile(String bucketName, String fileName) {
        String flag = "success";
        try {
            getClient().removeObject(bucketName, fileName);
        } catch (Exception e) {
            log.error("error when remove file , ", e);
            flag = "fail";
        }
        return flag;
    }

    /**
     * 获取上传对象的签名地址
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    @Override
    public String getPresignedObjectUrl(String fileName) throws Exception {
        try {
            return getClient().presignedGetObject(bucketName, fileName);
        } catch (Exception e) {
            log.error("error when get signedUrl, ", e);
            throw e;
        }
    }

    @Override
    public String getFileUrl(String fileName) throws Exception {
        MinioClient client = getClient();
        return client.getObjectUrl(bucketName, fileName);
    }

    @Override
    public InputStream downLoadFile(String fileName) throws Exception {
        MinioClient client = getClient();
        InputStream ins = client.getObject(bucketName, fileName);
        return ins;
    }

    @Override
    public void removeFile(String fileName) throws Exception {
        try {
            MinioClient client = getClient();
            client.removeObject(bucketName, fileName);
        } catch (Exception e) {
            log.error("error when upload cid, ", e);
            throw e;
        }
    }

    /**
     * 上传文件
     *
     * @param objectName
     * @param filePath
     * @throws Exception
     */
    @Override
    public void putFile(String objectName, String filePath) throws Exception {
        getClient().putObject(bucketName, objectName, filePath, null);
    }

    /**
     * 下载文件
     *
     * @param objectName
     * @param filePath
     * @throws Exception
     */
    @Override
    public void getFile(String objectName, String filePath) throws Exception {
        getClient().getObject(bucketName, objectName, filePath);
    }

    /**
     * 判断文件状态
     *
     * @param objectName
     * @return
     */
    @Override
    public ObjectStat statFile(String objectName) {
        try {
            return getClient().statObject(bucketName, objectName);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getUrl(String objectName) {
        try {
            return getClient().presignedGetObject(bucketName, objectName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取iotoss存储桶下所有存储的文件
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<FileVo> fileList() throws Exception {
        List<FileVo> fileVos = new ArrayList<>();
        String filesStr = "";
        String bucketName = "iotoss";
        Iterable<Result<Item>> listObjects = getClient().listObjects(bucketName);
        for (Result<Item> listObject : listObjects) {
            FileVo fileVo = new FileVo();
            fileVo.setBucketName(bucketName);
            fileVo.setFileName(listObject.get().objectName());
            fileVos.add(fileVo);
        }
        return fileVos;
    }

    @Override
    public List<FileVo> fileList(String prefix) throws Exception {
        List<FileVo> fileVos = new ArrayList<>();
        String filesStr = "";
        String bucketName = "iotoss";
        Iterable<Result<Item>> listObjects = getClient().listObjects(bucketName, prefix);
        for (Result<Item> listObject : listObjects) {
            FileVo fileVo = new FileVo();
            fileVo.setBucketName(bucketName);
            fileVo.setFileName(listObject.get().objectName());
            fileVos.add(fileVo);
        }
        return fileVos;
    }


    @Override
    public void downloadPackages(String downLoadPath, HttpServletResponse resp) throws Exception {
        resp.reset();
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(downLoadPath.substring(downLoadPath.lastIndexOf("/") + 1) + ".zip", "UTF-8"));
        resp.setContentType("application/octet-stream; charset=utf-8");
        resp.setCharacterEncoding("utf-8");
        Iterable<Result<Item>> listObjects = getClient().listObjects(bucketName, downLoadPath);
        try (ZipOutputStream out = new ZipOutputStream(resp.getOutputStream());) {
            for (Result<Item> listObject : listObjects) {
                String filePath = listObject.get().objectName();
                InputStream ins = downLoadFile(filePath);
                ZipEntry entry = new ZipEntry(filePath.substring(filePath.lastIndexOf(",") + 1));
                out.putNextEntry(entry);
                out.write(toByteArray(ins));
                out.closeEntry();
            }
            out.flush();
        }
    }

    @Override
    public boolean bucketExists(String mybucket) {
        try {
            boolean exists = getClient().bucketExists(bucketName + "/" + mybucket);
            return exists;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }

    }

    public byte[] toByteArray(InputStream input) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        }

    }
}
