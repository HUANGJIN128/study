package com.kim.study.service.ftp.impl;

import com.kim.study.common.config.FtpClientManagerConfig;
import com.kim.study.service.ftp.FtpUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        if(client==null){
            throw new RuntimeException("获取ftp连接失败");
        }
        boolean flag = false;
        InputStream in = new FileInputStream(localFile);
        String remote = new String(remoteFile.getBytes("GBK"),"iso-8859-1");
        if(client.storeFile(remote, in)){
            flag = true;
            log.info(localFile.getAbsolutePath()+"上传文件成功！");
        }else{
            flag=false;
            log.error(localFile.getAbsolutePath()+"上传文件失败！");
        }
        in.close();
        client.disconnect();
        return flag;

    }
    /**
     * 删除30天前的文件
     *
     * @param remotePath
     */
    @Override
    public void deleteFile(String remotePath) throws IOException {
        log.info("开始删除30天前文件");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        //获取一个月前的时间
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.MONTH,-1);
        String oneMothAgo = simpleDateFormat.format(instance.getTime());
        String filePrefix="portray_";
        FTPClient ftpClient = ftpClientManager.getClient();
        ftpClient.changeWorkingDirectory(remotePath);
        FTPFile[] ftpFiles = ftpClient.listFiles();
        for (FTPFile ftpFile : ftpFiles) {
            String name = ftpFile.getName();
            if(ftpFile.isFile()&&name.startsWith(filePrefix)){
                String fileTime = name.substring(8, name.indexOf("."));
                if(Integer.valueOf(fileTime)<Integer.valueOf(oneMothAgo)){
                    ftpClient.deleteFile(name);
                    log.info("删除{}文件成功",name);
                }
            }
        }
        ftpClient.disconnect();
    }

    /**
     * 下载文件
     * @return
     * @throws IOException
     */

    @Override
    public  boolean downFile() throws IOException {
        boolean isTrue = false;
        OutputStream os=null;
        String fileName="/tmp/userdriver/portray_20220601.csv";
        FTPClient ftpClient = ftpClientManager.getClient();
        ftpClient.changeWorkingDirectory("/tmp/userdriver");
        FTPFile[] ftpFiles = ftpClient.listFiles();
        for (FTPFile ftpFile : ftpFiles) {
            String name = ftpFile.getName();
            log.info("/tmp/userdriver目录下的文件--"+name);
        }
        os = new FileOutputStream("/opt/sgd/test.csv");
        isTrue = ftpClient.retrieveFile(new String(fileName.getBytes(),"ISO-8859-1"), os);
        os.close();
        ftpClient.disconnect();
        return isTrue;
    }



}
