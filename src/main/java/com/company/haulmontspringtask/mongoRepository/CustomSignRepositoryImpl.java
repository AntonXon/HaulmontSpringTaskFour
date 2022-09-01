package com.company.haulmontspringtask.mongoRepository;

import com.company.haulmontspringtask.dto.SignReportDto;
import com.company.haulmontspringtask.entity.ExamSheet;
import com.company.haulmontspringtask.entity.Sign;
import com.company.haulmontspringtask.entity.Teacher;
import com.company.haulmontspringtask.repository.ExamSheetRepository;
import com.company.haulmontspringtask.repository.TeacherRepository;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class CustomSignRepositoryImpl implements CustomSignRepository {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    SignRepository signRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    ExamSheetRepository examSheetRepository;
    @Autowired
    FetchPlans fetchPlans;

    @Override
    public Sign createSign(Teacher teacher, ExamSheet examSheet) {
        Sign checkSign = signRepository.findSignByExamSheetId(examSheet.getId().toString());
        if (checkSign != null) {
            throw new RuntimeException("Sign is already created");
        }
        Sign sign = new Sign();
        sign.setId(new ObjectId().toString());
        sign.setExamSheetId(examSheet.getId().toString());
        sign.setTeacherId(teacher.getId().toString());
        sign.setSignDate(Calendar.getInstance().getTime());
        return mongoTemplate.save(sign);
    }

    @Override
    public List<SignReportDto> getReport() {
        List<SignReportDto> result = new ArrayList<>();
        var teacherFetchPlan = fetchPlans.builder(Teacher.class)
                .addFetchPlan(FetchPlan.BASE)
                .add("user")
                .build();
        var examSheetFetchPlan = fetchPlans.builder(ExamSheet.class)
                .addFetchPlan(FetchPlan.BASE)
                .build();
        List<Sign> signList = signRepository.findAll();
        signList.forEach(sign -> {
            SignReportDto signReportDto = new SignReportDto();
            signReportDto.setSignDate(sign.getSignDate());
            try {
                Teacher teacher = teacherRepository.getById(UUID.fromString(sign.getTeacherId()), teacherFetchPlan);
                signReportDto.setTeacherName(teacher.getUser().getFirstName() + " " + teacher.getUser().getLastName());
                ExamSheet examSheet = examSheetRepository.getById(UUID.fromString(sign.getExamSheetId()), examSheetFetchPlan);
                signReportDto.setExamSheetNumber(examSheet.getNumber());
                result.add(signReportDto);
            } catch (Exception ignored){

            }
        });
        return result;
    }
}
