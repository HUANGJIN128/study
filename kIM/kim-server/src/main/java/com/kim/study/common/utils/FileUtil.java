package com.kim.study.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

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
    private void downLoad(InputStream fis, String tempPath) throws IOException {
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
            throw new RuntimeException(e);
        }finally {
            fis.close();
            outputStream.close();
        }
    }

    /**
     * json字符串保存到json文件中
     * @param str 字符串
     * @param path 文件路径
     * @return
     */
    public static boolean downloadToJsonFile(String str, String path) {
        boolean flag=false;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
            bufferedWriter.write(str);
            bufferedWriter.flush();
            flag=true;
            log.info("downloadToJsonFile ==> download longblob to json file [{}] success.", path);
        } catch (IOException e) {
            log.error("downloadToJsonFile ==> download longblob to json file error", e);
            throw new RuntimeException(e);

        }
        return flag;
    }


    /**
     * 删除临时文件
     * @param path 文件路径
     * @return
     */
    public static boolean deleteFile( String path) {
        File file=new File(path);
        boolean isDelete=false;
        try {
            isDelete = file.delete();
            log.info("deleteFile ==> Temp file [{}] deleted {}.", path, isDelete);
        } catch (Exception e) {
            log.error("deleteFile ==> Delete temp file error.", e);
            throw new RuntimeException(e);

        }

        return isDelete;

    }

    /**
     * 输入流转数组
     * @param is
     * @return
     */
    public static byte[] getByteAyyByInStream(InputStream is){

        byte[] data = null;
        try {
            data = IOUtils.toByteArray(is);
        } catch (IOException e) {
            log.error("readFileBytes, err", e);
            return data;
        }
        return data;
    }

}
