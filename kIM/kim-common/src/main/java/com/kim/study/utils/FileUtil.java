package com.kim.study.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName FileUtil
 * @Description
 * @Author KIM
 * @Date 2022/7/22 17:31
 * @Version 1.0
 */
public class FileUtil {

    /**
     * 将InputStream写入本地文件
     *
     * @param filePath 本地绝对路径
     * @param input       输入流
     * @throws IOException IOException
     */
    public static void writeToLocal(String filePath, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(filePath);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        input.close();
        downloadFile.close();
    }

}
