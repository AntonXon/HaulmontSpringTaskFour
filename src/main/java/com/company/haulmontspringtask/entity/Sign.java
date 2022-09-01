package com.company.haulmontspringtask.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Document("sign")
public class Sign {
    @Id
    private String id;
    private String teacherId;
    private String examSheetId;
    private Date signDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getExamSheetId() {
        return examSheetId;
    }

    public void setExamSheetId(String examSheetId) {
        this.examSheetId = examSheetId;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }
}
