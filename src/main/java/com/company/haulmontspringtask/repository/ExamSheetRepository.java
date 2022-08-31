package com.company.haulmontspringtask.repository;

import com.company.haulmontspringtask.entity.ExamSheet;
import com.company.haulmontspringtask.entity.Teacher;
import io.jmix.core.repository.JmixDataRepository;

import java.util.List;
import java.util.UUID;

public interface ExamSheetRepository extends JmixDataRepository<ExamSheet, UUID> {
    ExamSheet getExamSheetByNumber(String number);
    List<ExamSheet> getExamSheetsByTeacher(Teacher teacher);
}
