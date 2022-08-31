package com.company.haulmontspringtask.repository;

import com.company.haulmontspringtask.entity.ExamSheet;
import com.company.haulmontspringtask.entity.Teacher;
import com.company.haulmontspringtask.entity.User;
import com.company.haulmontspringtask.security.DatabaseUserRepository;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ExamSheetRepositoryTest {
    @Autowired
    protected DataManager dataManager;
    @Autowired
    ExamSheetRepository examSheetRepository;
    @Autowired
    SystemAuthenticator systemAuthenticator;

    @BeforeEach
    void setUp() {
        systemAuthenticator.begin();
    }

    @AfterEach
    void tearDown() {
        systemAuthenticator.end();
    }

    @Test
    void getExamSheetByNumber() {
        var user = dataManager.create(User.class);
        user.setUsername("TEST");
        var teacher = dataManager.create(Teacher.class);
        teacher.setUser(user);
        Random random = new Random();
        String number1 = ExamSheet.class.getName() + random.nextInt();
        var examSheet1 = dataManager.create(ExamSheet.class);
        examSheet1.setNumber(number1);
        examSheet1.setTeacher(teacher);
        String number2 = ExamSheet.class.getName() + random.nextInt();
        //добавим лишнее
        var examSheet2 = dataManager.create(ExamSheet.class);
        examSheet2.setNumber(number2);
        examSheet2.setTeacher(teacher);
        teacher.setExamSheets(Set.of(examSheet1, examSheet2));
        dataManager.save(teacher);
        var foundSheet = examSheetRepository.getExamSheetByNumber(number1);
        dataManager.remove(teacher);
        assertEquals(examSheet1.getId(), foundSheet.getId(), "Not equals");
    }

    @Test
    void getExamSheetsByTeacherEquals() {
        var user = dataManager.create(User.class);
        user.setUsername("TEST");
        var teacher = dataManager.create(Teacher.class);
        teacher.setUser(user);
        var examSheet1 = dataManager.create(ExamSheet.class);
        examSheet1.setNumber("1");
        examSheet1.setTeacher(teacher);
        var examSheet2 = dataManager.create(ExamSheet.class);
        examSheet2.setNumber("2");
        examSheet2.setTeacher(teacher);
        teacher.setExamSheets(Set.of(examSheet1, examSheet2));
        dataManager.save(teacher);
        List<UUID> foundIds = examSheetRepository.getExamSheetsByTeacher(teacher)
                .stream().sorted(Comparator.comparing(ExamSheet::getNumber)).map(ExamSheet::getId).collect(Collectors.toList());
        List<UUID> createdIds = List.of(examSheet1.getId(), examSheet2.getId());
        dataManager.remove(teacher);
        assertEquals(createdIds, foundIds, "Not equals");
    }
}
