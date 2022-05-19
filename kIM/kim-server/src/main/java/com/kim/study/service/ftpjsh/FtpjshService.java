package com.kim.study.service.ftpjsh;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName FtpjshService
 * @Description TODO
 * @Author KIM
 * @Date 2022/5/19 14:01
 * @Version 1.0
 */

public interface FtpjshService {

    public boolean uploadFile(String remoteFile, File localFile) throws IOException, SftpException, JSchException;

    public void closeConnect() throws JSchException;

    public void deleteFile(String directory) throws IOException, SftpException, JSchException;
}

