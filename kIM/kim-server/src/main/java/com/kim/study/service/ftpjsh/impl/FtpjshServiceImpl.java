/*
package com.kim.study.service.ftpjsh.impl;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.kim.study.common.config.FtpjschConfig;
import com.kim.study.service.ftpjsh.FtpjshService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

*/
/**
 * @ClassName FtpjshServiceImpl
 * @Description TODO
 * @Author KIM
 * @Date 2022/5/19 14:02
 * @Version 1.0
 *//*

@Slf4j
@Service
public class FtpjshServiceImpl implements FtpjshService {
    @Autowired
    private FtpjschConfig ftpjschConfig;
    @Override
    public boolean uploadFile(String remoteFile, File localFile) throws IOException, SftpException, JSchException {
        ChannelSftp client = ftpjschConfig.getClient();
        if(client==null){
            throw new RuntimeException("获取ftp连接失败");
        }
        boolean flag = false;
        InputStream in = new FileInputStream(localFile);
        String remote = new String((remoteFile).getBytes("GBK"),"iso-8859-1");
        try {
            client.put(in, remote);
            flag = true;
            log.info(localFile.getAbsolutePath()+"上传文件成功！");
        } catch (SftpException e) {
            flag=false;
            log.error(localFile.getAbsolutePath()+"上传文件失败！");
            e.printStackTrace();
        }
        in.close();
        return flag;
    }

    @Override
    public void closeConnect() throws JSchException {
        ChannelSftp channel = ftpjschConfig.getClient();
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
                ftpjschConfig.setSftp(null);
            }
        }
    }

    @Override
    public void deleteFile(String remotePath) throws SftpException, JSchException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        //获取一个月前的时间
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.MONTH,-1);
        String oneMothAgo = simpleDateFormat.format(instance.getTime());
        String filePrefix="user_info_";
        ChannelSftp channel = ftpjschConfig.getClient();
        channel.cd(remotePath);
        Vector vector = channel.ls(remotePath);
        Iterator iterator = vector.iterator();
        while (iterator.hasNext()) {
            ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) iterator.next();
            String name = file.getFilename();
            if(name.startsWith(filePrefix)){
                //判断是否为目录
                try {
                    String duelPath=remotePath+File.separator+name;
                    channel.cd(duelPath);
                } catch (SftpException e) {
                    String fileTime = name.substring(10, name.indexOf("."));
                    if(Integer.valueOf(fileTime)<Integer.valueOf(oneMothAgo)){
                        channel.rm(name);
                    }
                    channel.cd(remotePath);
                    continue;

                }
            }
        }
    }
}
*/
