package com.company.haulmontspringtask.dto;

import java.util.Date;

public class SignReportDto {
    private String examSheetNumber;
    private String teacherName;
    private Date signDate;

    public String getExamSheetNumber() {
        return examSheetNumber;
    }

    public SignReportDto setExamSheetNumber(String examSheetNumber) {
        this.examSheetNumber = examSheetNumber;
        return this;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public SignReportDto setTeacherName(String teacherName) {
        this.teacherName = teacherName;
        return this;
    }

    public Date getSignDate() {
        return signDate;
    }

    public SignReportDto setSignDate(Date signDate) {
        this.signDate = signDate;
        return this;
    }
}
