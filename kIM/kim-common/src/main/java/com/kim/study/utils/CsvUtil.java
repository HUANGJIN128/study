package com.kim.study.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @ClassName CsvUtil
 * @Description 导出csv文件工具类
 * @Author KIM
 * @Date 2022/5/17 13:14
 * @Version 1.0
 */
@Slf4j
public class CsvUtil {
    //CSV文件分隔符
    private final static String NEW_LINE_SEPARATOR="\n";
    /** CSV文件列分隔符 */
    private static final String CSV_COLUMN_SEPARATOR = "^A";
    /** CSV文件列分隔符 */
    private static final String CSV_RN = "\r\n";
    /**
     * DES加密key
     */
    private static final String DES_KEY = "bigdataR";

    /**写入csv文件 创建表头,所有数据一次性写入
     * @param headers 列头
     * @param data 数据内容
     * @param filePath 创建的csv文件路径
     * @throws IOException **/
    public static void writeCsvWithHeader(String[] headers, List<Object[]> data, String filePath) {
        //初始化csvformat
        CSVFormat format = CSVFormat.DEFAULT.withHeader(headers);
        try {
            //根据路径创建文件，并设置编码格式
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
            //创建CSVPrinter对象
            CSVPrinter printer = new CSVPrinter(osw, format);

            if(null!=data){
                //循环写入数据
                for(Object[] lineData:data){
                    printer.printRecord(lineData);
                }
            }
            printer.flush();
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**写入csv文件 可以分批写入,创建一次表头
     * @param headers 列头
     * @param data 数据内容
     * @param filePath 创建的csv文件路径
     * @throws IOException **/
    public static void writeCsvWithRecordSeparator(Object[] headers, List<Object[]> data, String filePath){
        //初始化csvformat
        CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        try {
            //根据路径创建文件，并设置编码格式
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
            //创建CSVPrinter对象
            CSVPrinter printer = new CSVPrinter(osw,format);
            //写入列头数据
            printer.printRecord(headers);

            if(null!=data){
                //循环写入数据
                for(Object[] lineData:data){
                    printer.printRecord(lineData);
                }
            }
            printer.flush();
            printer.close();
            System.out.println("CSV文件创建成功,文件路径:"+filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**es数据分批写入csv文件
     *
     * @param filePath 创建的csv文件路径
     * @throws IOException **//*
    public static void writeCsvWithRecordSeparator(CSVFormat format, SearchHit[] hitsResult, String filePath,String mblphNo,List<String> initTableInfo,Integer page){
        List<Object[]> dataList=new ArrayList<>();
        for (SearchHit  documentFields: hitsResult) {
            List<Object> data=new ArrayList<>();
            Map<String, Object> map = documentFields.getSourceAsMap();
            String mblphNoValue= (String) map.get(mblphNo);
            //对用户手机号进行Des加密处理
            String encryptMbl = DESUtil.encrypt(mblphNoValue, DES_KEY);
            map.put(mblphNo,encryptMbl);
            for (String column : initTableInfo) {
                Object value = map.get(column);
                data.add(String.valueOf(value));
            }
            Object[] objects = data.toArray();
            dataList.add(objects);
        }
        try {
            //根据路径创建文件，并设置编码格式
            FileOutputStream fos = new FileOutputStream(filePath,true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
            //创建CSVPrinter对象
            CSVPrinter printer = new CSVPrinter(osw, format);
            if(page==0){
                //写入列头数据
                printer.printRecord(initTableInfo.toArray());
            }
            if(null!=dataList){
                //循环写入数据
                for(Object[] lineData:dataList){
                    printer.printRecord(lineData);
                }
            }
            printer.flush();
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public static void writeCsvWithRecordSeparator(CSVFormat format, List<Map> hitsResult, String filePath,String mblphNo,List<String> initTableInfo,Integer page){
        List<Object[]> dataList=new ArrayList<>();
        for (Map  map: hitsResult) {
            List<Object> data=new ArrayList<>();
            //Map<String, Object> map = documentFields.getSourceAsMap();
            String mblphNoValue= (String) map.get(mblphNo);
            //mblphNoValue=mblphNoValue.substring(0,3)+"****"+mblphNoValue.substring(8);
            String encryptMbl = DESUtil.encrypt(mblphNoValue, DES_KEY);
            map.put(mblphNo,encryptMbl);

            for (String column : initTableInfo) {
                Object value = map.get(column);
                data.add(value);
            }
            Object[] objects = data.toArray();
            dataList.add(objects);
        }
        try {
            //根据路径创建文件，并设置编码格式
            FileOutputStream fos = new FileOutputStream(filePath,true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
            //创建CSVPrinter对象
            CSVPrinter printer = new CSVPrinter(osw, format);
            if(page==0){
                //写入列头数据
                printer.printRecord(initTableInfo.toArray());
            }
            if(null!=dataList){
                //循环写入数据
                for(Object[] lineData:dataList){
                    printer.printRecord(lineData);
                }
            }
            printer.flush();
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @filePath 文件路径
     */
    public static List<CSVRecord> readCsvParse(String filePath){
        List<CSVRecord> records = new ArrayList<>();
        try {
            FileInputStream in = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader (new InputStreamReader(in,"GBK"));
            CSVParser parser = CSVFormat.EXCEL.parse(reader);
            records = parser.getRecords();
            parser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return records;
        }
    }


    /**流形式输出到前端
     * @param colNames 表头部数据
     * @param dataList 集合数据
     * @param mapKeys 查找的对应数据
     */
    public static ByteArrayOutputStream doExport(String[] colNames, String[] mapKeys, List<Map> dataList) {
        try {
            StringBuffer buf = new StringBuffer();

            // 完成数据csv文件的封装
            // 输出列头
            for (int i = 0; i < colNames.length; i++) {
                buf.append(colNames[i]).append(CSV_COLUMN_SEPARATOR);
            }
            buf.append(CSV_RN);
            // 输出数据
            if (null != dataList) {
                for (int i = 0; i < dataList.size(); i++) {
                    for (int j = 0; j < mapKeys.length; j++) {
                        buf.append(dataList.get(i).get(mapKeys[j])).append(CSV_COLUMN_SEPARATOR);
                    }
                    buf.append(CSV_RN);
                }
            }
            // 写出响应
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            //OutputStream os = new ByteArrayOutputStream();
            os.write(buf.toString().getBytes("GBK"));
            os.flush();
            os.close();
            return os;
        } catch (Exception e) {
            log.error("doExport错误...", e);
            e.printStackTrace();
        }
        return null;
    }



    /*public static ByteArrayOutputStream doExport(List<String> colNames, SearchHit[] hitsResult,String mblphNo,String filePath,Integer page) {
        try {
            StringBuffer buf = new StringBuffer();


            // 完成数据csv文件的封装
            // 输出列头
            if(page==0){
                for (int i = 0; i < colNames.size(); i++) {
                    buf.append(colNames.get(i)).append(CSV_COLUMN_SEPARATOR);
                }
                buf.append(CSV_RN);
            }
            // 输出数据
            if (null != hitsResult) {
                for (SearchHit documentFields : hitsResult) {
                    List<Object> data=new ArrayList<>();
                    Map<String, Object> map = documentFields.getSourceAsMap();
                    String mblphNoValue= (String) map.get(mblphNo);
                    //对用户手机号进行Des加密处理
                    String encryptMbl = DESUtil.encrypt(mblphNoValue, DES_KEY);
                    map.put(mblphNo,encryptMbl);
                    for (String column : colNames) {
                        Object value = map.get(column);
                        data.add(String.valueOf(value));
                    }
                    for (Object datum : data) {
                        buf.append(String.valueOf(datum)).append(CSV_COLUMN_SEPARATOR);
                    }
                    buf.append(CSV_RN);

                }
            }
            // 写出响应
            FileOutputStream fos = new FileOutputStream(filePath,true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
            osw.write(buf.toString());
            osw.flush();
            osw.close();
            //return os;
        } catch (Exception e) {
            log.error("doExport错误...", e);
            e.printStackTrace();
        }
        return null;
    }*/




























    public static HttpHeaders setCsvHeader(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        try {
            // 设置文件后缀
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = new String(fileName.getBytes("gbk"), "iso8859-1") + sdf.format(new Date()) + ".csv";
            headers.add("Pragma", "public");
            headers.add("Cache-Control", "max-age=30");
            headers.add("Content-Disposition", "attachment;filename="+filename);
            headers.setContentType(MediaType.valueOf("application/vnd.ms-excel;charset=UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return headers;
    }



}