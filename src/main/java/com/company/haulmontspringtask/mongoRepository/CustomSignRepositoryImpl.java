package com.company.haulmontspringtask.mongoRepository;

import com.company.haulmontspringtask.entity.ExamSheet;
import com.company.haulmontspringtask.entity.Sign;
import com.company.haulmontspringtask.entity.Teacher;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomSignRepositoryImpl implements CustomSignRepository {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    SignRepository signRepository;

    @Override
    public Sign createSign(Teacher teacher, ExamSheet examSheet) {
        Sign checkSign = signRepository.findSignByExamSheetId(examSheet.getId().toString());
        if (checkSign != null){
            throw new RuntimeException("Sign is already created");
        }
        Sign sign = new Sign();
        sign.setId(new ObjectId().toString());
        sign.setExamSheetId(examSheet.getId().toString());
        sign.setTeacherId(teacher.getId().toString());
        sign.setSignDate(Calendar.getInstance().getTime());
        return mongoTemplate.save(sign);
    }
}
