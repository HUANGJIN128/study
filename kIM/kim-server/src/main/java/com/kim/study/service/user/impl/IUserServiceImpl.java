package com.kim.study.service.user.impl;

import com.grapecity.documents.excel.IRange;
import com.grapecity.documents.excel.IWorksheet;
import com.grapecity.documents.excel.IWorksheets;
import com.grapecity.documents.excel.Workbook;
import com.kim.study.entity.UserEntity;
import com.kim.study.repository.IUserReporsitory;
import com.kim.study.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @ClassName IUserServiceImpl
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/2 14:40
 * @Version 1.0
 */
@Service
public class IUserServiceImpl<T> implements IUserService {

    @Autowired
    private IUserReporsitory iUserReporsitory;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Executor executor;


    @Override
    public UserEntity getUserByUserName(String name) {
        UserEntity byName = iUserReporsitory.findByCode(name);
        String code = byName.getCode();
        redisTemplate.opsForValue().set(code,"hahahahahahha");
        String o = (String) redisTemplate.opsForValue().get(byName.getCode());
        //异步测试代码,同时可以写一个方法,上面加@Async("taskExecutor")注解实现异步操作
        //executor.execute(new test());
        System.out.println(o);
        return byName;
    }

    @Override
    public void updateLode( MultipartFile multipartFile) throws IOException {
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
        //IWorksheet sheet1 = workbook.getWorksheets().get("Sheet1");
        excelData=excelData.stream().filter(s->!(s==null)).collect(Collectors.toList());
        System.out.println(excelData);
    }
}
