package com.kim.study.utils;

import com.alibaba.fastjson.JSONObject;
import freemarker.template.utility.NullArgumentException;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Author sunyt
 * @Date 2022/6/6 10:02
 * @Description Minio 相关处理工具类
 */
@Slf4j
@Component
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;

    /**
     * 下载文件
     *
     * @param name     当前文件名称
     * @param response
     * @param fileName 要返回的excel文件名称
     */
    public void downLoad(String name, HttpServletResponse response, String fileName, String bucketName) {
        try(InputStream fileInputStream = minioClient.getObject(bucketName, name)) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("MinioUtil downLoad file [{}] ,bucket name [{}] fail",name, bucketName,e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @param name 当前文件名称
     * @return
     */
    public void uploadFile(MultipartFile file, String name, String bucketName) {
        try(InputStream inputStream = file.getInputStream()) {
            //存入bucket不存在则创建
            createBucket(bucketName);
            // 存储文件
            minioClient.putObject(bucketName, name, inputStream, file.getContentType());
        } catch (Exception e) {
            log.error("File upload error！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建minio桶
     *
     * @param bucketName
     */
    private void createBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(bucketName)) {
                minioClient.makeBucket(bucketName);
            }
        } catch (Exception e) {
            log.error("Create bucket [{}] fail ", bucketName);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据文件对应id 删除文件
     *
     * @param bucketName
     * @param name       当前文件名称
     * @return
     */
    public void delMinioFile(String bucketName, String name) {
        try {
            minioClient.removeObject(bucketName, name);
        } catch (Exception e) {
            log.error("Minio delete error", e);
            throw new RuntimeException("删除文件失败");
        }

    }


    /**
     * 上传文件
     *
     * @param name 文件名称
     * @param path 上传本地文件路径
     * @return
     */
    public void uploadFileByFile(String path, String name, String bucketName) {
        if (StringUtils.isEmpty(bucketName)) {
            throw new NullArgumentException("UploadFileByFile BucketName is null");
        }
        //存入bucket不存在则创建
        createBucket(bucketName);
        try (InputStream fileInput = new FileInputStream(path)) {
            minioClient.putObject(bucketName, name, fileInput, "application/octet-stream");
        } catch (NullPointerException nullPointerException) {
            log.error("File upload error", nullPointerException);
        } catch (Exception e) {
            log.error("File upload error！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 从minio获取大字段信息
     *
     * @param buketName 桶名称
     * @param name      文件名称
     * @return
     */
    public Map getMinioDataById(String buketName, String name) {
        Map map = null;
        try {
            InputStream fileInputStream = minioClient.getObject(buketName, name);
            byte[] byteAyyByInStream = FileUtil.getByteAyyByInStream(fileInputStream);
            String data = new String(byteAyyByInStream, "utf-8");
            map = JSONObject.parseObject(data, Map.class);
        } catch (Exception e) {
            log.error("get longblob json data fail buketName={} id={}", buketName, name, e);
            throw new RuntimeException(e);
        }
        return map;
    }
}
