package com.company.haulmontspringtask.mongoRepository;

import com.company.haulmontspringtask.dto.SignReportDto;
import com.company.haulmontspringtask.entity.ExamSheet;
import com.company.haulmontspringtask.entity.Sign;
import com.company.haulmontspringtask.entity.Teacher;

import java.util.List;

public interface CustomSignRepository {
    Sign createSign(Teacher teacher, ExamSheet examSheet);

    List<SignReportDto> getReport();
}
