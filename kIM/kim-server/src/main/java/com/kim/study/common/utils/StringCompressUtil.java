package com.kim.study.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author : Kim.
 * Create Date  : 2022-03-16
 * Create Time  : 11:25
 * Description  : 字符串解压缩
 *
 */


@Slf4j
public class StringCompressUtil {
    private StringCompressUtil() {
    }

/**
     * 使用gzip压缩字符串
     *
     * @param str 要压缩的字符串
     * @return 压缩后的字符串
     */

    public static String compress(String str) {
        if (str == null || str.length() <= 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("StringCompressUtil error: {}，", str, e);
            throw new RuntimeException("字符串压缩失败");
        }
        return Base64.encodeBase64String(out.toByteArray());
    }

/**
     * 使用gzip解压缩
     *
     * @param compressedStr 压缩字符串
     * @return 解压后的字符串
     */

    public static String uncompress(String compressedStr) {
        if (compressedStr == null || compressedStr.length() <= 0) {
            return compressedStr;
        }
        String decompressed;
        byte[] compressed = Base64.decodeBase64(compressedStr);
        try (
                ByteArrayInputStream in = new ByteArrayInputStream(compressed);
                GZIPInputStream gzip = new GZIPInputStream(in);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {

            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = gzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            log.error("StringCompressUtil error: {},", compressedStr, e);
            throw new RuntimeException("字符串解压失败");
        }
        return decompressed;
    }
}
