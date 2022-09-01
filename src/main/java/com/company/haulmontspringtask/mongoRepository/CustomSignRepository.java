package com.company.haulmontspringtask.mongoRepository;

import com.company.haulmontspringtask.entity.ExamSheet;
import com.company.haulmontspringtask.entity.Sign;
import com.company.haulmontspringtask.entity.Teacher;

public interface CustomSignRepository {
    Sign createSign(Teacher teacher, ExamSheet examSheet);
}
