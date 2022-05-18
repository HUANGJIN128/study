package com.kim.study.config;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
/**
 * @ClassName FtpUploadServiceImpl
 * @Description FtpClient配置类
 * @Author KIM
 * @Date 2022/5/17 15:44
 * @Version 1.0
 */
@Component
public class FtpClientManagerConfig {
    private static Logger logger = LoggerFactory.getLogger(FtpClientManagerConfig.class);

    @Value("${ftp.ip}")
    private String ip;

    @Value("${ftp.port}")
    private Integer port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    private FTPClient ftpClient = null;

    public FTPClient getClient() {
        if(this.ftpClient == null){
            this.initClient();
        }
        return this.ftpClient;
    }

    private void initClient() {
        if (this.ftpClient == null) {
            ftpClient = new FTPClient();
            try {
                ftpClient.connect(ip,22);
                ftpClient.login(username, password);
                int reply = ftpClient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftpClient.disconnect();
                }
                logger.info("success to connect ftp server");
            } catch (IOException e) {
                logger.error("faild to connect ftp server because " + e.getMessage());
                this.ftpClient=null;
            }
        }
    }
}
