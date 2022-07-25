package com.kim.study.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @ClassName FileUtil
 * @Description
 * @Author KIM
 * @Date 2022/7/22 17:31
 * @Version 1.0
 */
@Slf4j
public class FileUtil {

    /**
     *下载文件到服务器
     * @param fis 输入流
     * @param tempPath 下载绝对路径
     * @throws IOException
     */
    private void downLoadExcel(InputStream fis, String tempPath) throws IOException {
        OutputStream outputStream=null;
        try {
            File file=new File(tempPath);
            outputStream=new FileOutputStream(file);
            int index;
            byte[] bytes = new byte[1024];
            while ((index = fis.read(bytes)) != -1) {
                outputStream.write(bytes, 0, index);
            }
        } catch (Exception e) {
            log.error("uploadexcel to temp dir fail", e);
        }finally {
            fis.close();
            outputStream.close();
        }
    }

}
