package com.kim.study.common.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class CheckHeapInspectionUtil {
    public CheckHeapInspectionUtil() {
    }

    public static StringBuffer checkToUTF8(byte[] decByte) {
        StringBuffer decs = new StringBuffer();
        Charset charset = Charset.forName("UTF-8");
        return getStringBuffer(decByte, decs, charset);
    }

    public static StringBuffer checkToGBK(byte[] decByte) {
        StringBuffer decs = new StringBuffer();
        Charset charset = Charset.forName("GBK");
        return getStringBuffer(decByte, decs, charset);
    }

    private static StringBuffer getStringBuffer(byte[] decByte, StringBuffer decs, Charset charset) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(decByte.length);
        byteBuffer.put(decByte);
        byteBuffer.flip();
        CharBuffer charBuffer = charset.decode(byteBuffer);
        char[] charBufferArray = charBuffer.array();

        for(int i = 0; i < charBuffer.length(); ++i) {
            decs.append(charBufferArray[i]);
        }

        return decs;
    }
}
