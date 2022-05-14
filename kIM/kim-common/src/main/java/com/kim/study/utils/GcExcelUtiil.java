package com.kim.study.utils;

import com.grapecity.documents.excel.IRange;
import com.grapecity.documents.excel.IWorksheet;
import com.grapecity.documents.excel.IWorksheets;
import com.grapecity.documents.excel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName GcExcelUtiil
 * @Description GcExcel解析文件工具类
 * @Author KIM
 * @Date 2022/5/14 22:54
 * @Version 1.0
 */
public class GcExcelUtiil {

    public List<Map> updateLode( MultipartFile multipartFile) throws IOException {
        Workbook workbook = new Workbook();
        InputStream inputStream = multipartFile.getInputStream();
        workbook.open(inputStream);
        inputStream.close();
        IWorksheets worksheets = workbook.getWorksheets();
        Iterator<IWorksheet> iterator = worksheets.iterator();
        List<Map> excelData=new ArrayList<>();
        while (iterator.hasNext()){
            IWorksheet sheet = iterator.next();
            int rowCount = sheet.getRowCount();
            IRange rows = sheet.getRows();
            for (int i = 1; i < rowCount-1; i++) {
                HashMap<String, Object> objectObjectHashMap = new LinkedHashMap<>();
                Object value = rows.get(i).getValue();
                Object[][] valueArrr= (Object[][]) value;
                //第一行也就是列名
                Object firstLine = rows.get(0).getValue();
                Object[][] firstLineArr= (Object[][]) firstLine;
                int length = valueArrr[0].length;
                for (int i1 = 0; i1 < length; i1++) {
                    if(valueArrr[0][i1] instanceof String){
                        String cell= (String) valueArrr[0][i1];
                        String headcell= (String) firstLineArr[0][i1];
                        objectObjectHashMap.put(headcell,cell);
                        excelData.add(objectObjectHashMap);
                    }else if (valueArrr[0][i1] instanceof Date){
                        Date cell= (Date) valueArrr[0][i1];
                        String headcell= (String) firstLineArr[0][i1];
                        objectObjectHashMap.put(headcell,cell);
                        excelData.add(objectObjectHashMap);
                    }else if(valueArrr[0][i1] instanceof Integer){
                        Integer cell= (Integer) valueArrr[0][i1];
                        String headcell= (String) firstLineArr[0][i1];
                        objectObjectHashMap.put(headcell,cell);
                        excelData.add(objectObjectHashMap);
                    }
                }

            }
        }
        excelData=excelData.stream().filter(s->!(s==null)).collect(Collectors.toList());
        return excelData;
    }
}
