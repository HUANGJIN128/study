package com.kim.study.common.config;

import com.jcraft.jsch.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @ClassName FtpjschConfig
 * @Description TODO
 * @Author KIM
 * @Date 2022/5/19 13:49
 * @Version 1.0
 */
@Component
@Data
public class FtpjschConfig {

    private  Channel channel=null;
    private  Session sshSession=null;
    private  ChannelSftp sftp = null;
    private JSch jsch=null;

    @Value("${ftp.ip}")
    private String ip;

    @Value("${ftp.port}")
    private Integer port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    public ChannelSftp getClient() throws JSchException {
        if(this.sftp == null){
            this.initClient();
        }
        return this.sftp;
    }

    private void initClient() {
        if(sftp==null){
            try {
                JSch jsch = new JSch();
                //获取sshSession 账号-ip-端口
                sshSession =jsch.getSession(username, ip,port);
                //添加密码
                sshSession.setPassword(password);
                Properties sshConfig = new Properties();
                //严格主机密钥检查
                sshConfig.put("StrictHostKeyChecking", "no");

                sshSession.setConfig(sshConfig);
                //开启sshSession链接
                sshSession.connect();
                //获取sftp通道
                channel = sshSession.openChannel("sftp");
                //开启
                channel.connect();
                sftp = (ChannelSftp) channel;
            } catch (Exception e) {
                sftp=null;
                e.printStackTrace();
            }
        }
    }
    public void closeConnect() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
                sftp=null;
            }
        }
    }
}
