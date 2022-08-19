package com.kim.study.utils;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.kim.study.entity.StudentEntity;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @ClassName PoiUtil
 * @Description
 * @Author KIM
 * @Date 2022/7/29 17:23
 * @Version 1.0
 */
public class PoiUtil {

    private List<StudentEntity> data=new ArrayList<>();

    public static void download(List<StudentEntity> data,String sheetName){
        if(ObjectUtil.isEmpty(data)){
            return;
        }
        HSSFWorkbook  workbook = new HSSFWorkbook();
        HSSFSheet sheetInd = workbook.createSheet("文件名称");
        StudentEntity studentEntity = data.get(0);
        LinkedHashMap map = JSONObject.parseObject(JSONObject.toJSONString(studentEntity), LinkedHashMap.class);
        //字段名写入表头
        Iterator iterator = map.entrySet().iterator();

    }


}
