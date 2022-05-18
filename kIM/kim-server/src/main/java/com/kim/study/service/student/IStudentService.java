package com.kim.study.service.student;

import com.kim.study.dto.PageDto;
import com.kim.study.resultbody.ResultPage;

/**
 * Description:
 * Author:KIM
 * Date:2022-03-01
 * Time:17:54
 */
public interface IStudentService {
    void saveStudent();

    ResultPage queryPage(PageDto pageDto);
}
