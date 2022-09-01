package com.company.haulmontspringtask.mongoRepository;


import com.company.haulmontspringtask.entity.Sign;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface SignRepository extends MongoRepository<Sign, String>, CustomSignRepository{
    Sign findSignByExamSheetId(String examSheetId);
    List<Sign> findSignsByTeacherId(String teacherId);
    List<Sign> findSignsBySignDateBetween(Date from, Date to);
}
