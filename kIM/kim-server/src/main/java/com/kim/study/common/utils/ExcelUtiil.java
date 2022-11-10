package com.kim.study.common.utils;

import cn.hutool.core.util.ObjectUtil;
import com.monitorjbl.xlsx.StreamingReader;
import freemarker.template.utility.NullArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ExcelUtiil 可以处理大数据上传（百万级别）
 * @Description
 * @Author KIM
 * @Date 2022/7/23 19:00
 * @Version 1.0
 */
@Slf4j
public class ExcelUtiil {
    /**
     * 解析本地excel文件
     * @param maxCount 最大解析行数,null则全部解析;
     * @param tempPath
     * @return
     * @throws IOException
     */
    private List<List<Object>> getExcelData(String tempPath,Integer maxCount) throws IOException {
        InputStream inputStream=new FileInputStream(new File(tempPath));
        List<List<Object>> sheetData= null;
        Workbook wb=null;
        Integer cellBeginIndex=-1;
        Integer cellEndIndex=-1;

        try {
            wb= StreamingReader.builder()
                    //读取到内存中的行数，默认10
                    .rowCacheSize(100)
                    //读取资源，缓存到内存的字节大小。默认1024
                    .bufferSize(4096)
                    //打开资源。只能是xlsx文件
                    .open(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            if (sheet == null) {
                throw new NullArgumentException();
            }
            sheetData = new ArrayList<>();
            for (Row row : sheet) {
                if(row==null){
                    continue;
                }
                cellBeginIndex=getCellBeginIndex(cellBeginIndex, row);
                cellEndIndex= getEndIndex(cellEndIndex, row);

                List<Object> rowData=new ArrayList<>();
                for (  int i=cellBeginIndex; i <=cellEndIndex; i++) {
                    Cell cell = row.getCell(i);
                    Object cellValue =getCellObjValue(cell);
                    rowData.add(cellValue);
                }
                sheetData.add(rowData);
                if(!ObjectUtil.isEmpty(maxCount)&&sheetData.size()==maxCount){
                    break;
                }
            }

        } catch (Exception e) {
            log.error("file temp parse fail",e);
            e.printStackTrace();
        } finally {
            if (ObjectUtil.isNotEmpty(wb)) {
                wb.close();
            }
        }
        return sheetData;
    }

    /**
     * 获取表头开始非空索引
     * @param cellBeginIndex
     * @param row
     * @return
     */
    private Integer getCellBeginIndex(Integer cellBeginIndex, Row row) {
        while(cellBeginIndex ==-1){
            for (Integer i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                Object cellValue = getCellObjValue(cell);
                if(cellValue!=null) {
                    cellBeginIndex =i;
                    break;
                }
            }
        }
        return cellBeginIndex;
    }

    /**
     * 获取表头结束非空索引
     * @param cellEndIndex
     * @param row
     * @return
     */
    private Integer getEndIndex(Integer cellEndIndex, Row row) {
        while(cellEndIndex ==-1){
            for (Integer i = row.getLastCellNum()-1; i >=0; i--) {
                Cell cell = row.getCell(i);
                Object cellValue = getCellObjValue(cell);
                if(cellValue!=null) {
                    cellEndIndex =i;
                    break;
                }
            }
        }
        return cellEndIndex;
    }

    public static String getCellValue(Cell cell, String value) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Object cellObjValue = getCellObjValue(cell);
        if (ObjectUtil.isEmpty(cellObjValue)) {
            return null;
        }
        if (cellObjValue instanceof Date) {
            value = df.format(cellObjValue);
        } else {
            value = String.valueOf(cellObjValue);
        }
        return value;
    }

    public static Object getCellObjValue(Cell cell) {
        if(cell==null){
            return null;
        }
        Object value = null;
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            //包含日期和普通数字
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue();
                } else {
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            default:
                if (cell != null) {
                    value = cell.toString();
                }
        }
        return value;
    }
}
