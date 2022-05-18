package com.kim.study.controller.ftp;

import com.kim.study.resultbody.ResultBody;
import com.kim.study.service.ftp.FtpUploadService;
import com.kim.study.utils.CsvUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName FtpController
 * @Description TODO
 * @Author KIM
 * @Date 2022/5/18 12:12
 * @Version 1.0
 */

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/kim/ftp")
public class FtpController {

    @Autowired
    private FtpUploadService ftpUploadService;

    //CSV文件分隔符
    private final static String NEW_LINE_SEPARATOR="\n";

    private final static String csvTempAdd="E:\\develop";

    /**
     *
     * @return
     * @throws IOException
     */
    @GetMapping(value = {"/cvstest"})
    @ApiOperation(value = "cvstest", notes = "cvstest")
    public ResultBody cvstest() throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateStr = simpleDateFormat.format(date);
        String remorePath="es-data"+dateStr+"-"+date.getTime()+".csv";
        List<Map> mapList=new ArrayList();
        List<String> tableNameList= Arrays.asList("姓名","电话");
        List<String> tableColumnsList=Arrays.asList("name","MBLPH_NO");
        Map initmap=new HashMap();
        initmap.put("name",tableNameList);
        initmap.put("headercolumns",tableColumnsList);

        for (int i = 0; i < 10; i++) {
            Map<String,Object> map=new HashMap<>();
            map.put("name","张三"+i);
            map.put("MBLPH_NO","1351082390"+i);
            mapList.add(map);
        }
        //初始化csvformat
        CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        for (int i = 0; i < 2; i++) {
            CsvUtil.writeCsvWithRecordSeparator(csvFormat,mapList,csvTempAdd+ File.separator+"test.csv","MBLPH_NO",initmap,i);
        }
        boolean b = ftpUploadService.uploadFile(remorePath, new File("E:\\develop\\test.csv"));
        return ResultBody.okResult("成功");
    }


}
