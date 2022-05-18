package com.kim.study.service.ftp;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName FtpUploadService
 * @Description TODO
 * @Author KIM
 * @Date 2022/5/17 15:43
 * @Version 1.0
 */
public interface FtpUploadService {

    public boolean uploadFile(String remoteFile, File localFile) throws IOException;

}
