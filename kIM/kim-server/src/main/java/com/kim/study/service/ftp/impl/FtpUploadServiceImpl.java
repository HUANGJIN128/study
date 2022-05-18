package com.kim.study.service.ftp.impl;

import com.kim.study.config.FtpClientManagerConfig;
import com.kim.study.service.ftp.FtpUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName FtpUploadServiceImpl
 * @Description TODO
 * @Author KIM
 * @Date 2022/5/17 15:44
 * @Version 1.0
 */
@Service
@Slf4j
public class FtpUploadServiceImpl implements FtpUploadService {

    @Autowired
    private FtpClientManagerConfig ftpClientManager;

   @Override
    public boolean uploadFile(String remoteFile, File localFile) throws IOException {
       FTPClient client = ftpClientManager.getClient();
       boolean flag = false;
       InputStream in = new FileInputStream(localFile);
       String remote = new String(remoteFile.getBytes("GBK"),"iso-8859-1");
       if(client.storeFile(remote, in)){
           flag = true;
           log.info(localFile.getAbsolutePath()+"上传文件成功！");
       }else{
           log.error(localFile.getAbsolutePath()+"上传文件失败！");
       }
       in.close();
       return flag;

    }
}
