package com.company.haulmontspringtask.repository;

import com.company.haulmontspringtask.BaseTest;
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
public class ExamSheetRepositoryTest extends BaseTest {
    @Autowired
    ExamSheetRepository examSheetRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void getExamSheetByNumber() {
        var user = userRepository.create();
        user.setUsername("TEST");
        var teacher = teacherRepository.create();
        teacher.setUser(user);
        Random random = new Random();
        String number1 = ExamSheet.class.getName() + random.nextInt();
        var examSheet1 = examSheetRepository.create();
        examSheet1.setNumber(number1);
        examSheet1.setTeacher(teacher);
        String number2 = ExamSheet.class.getName() + random.nextInt();
        //добавим лишнее
        var examSheet2 = examSheetRepository.create();
        examSheet2.setNumber(number2);
        examSheet2.setTeacher(teacher);
        teacher.setExamSheets(Set.of(examSheet1, examSheet2));
        teacherRepository.save(teacher);
        var foundSheet = examSheetRepository.getExamSheetByNumber(number1);
        teacherRepository.delete(teacher);
        assertEquals(examSheet1.getId(), foundSheet.getId(), "Not equals");
    }

    @Test
    void getExamSheetsByTeacherEquals() {
        var user = userRepository.create();
        user.setUsername("TEST");
        var teacher = teacherRepository.create();
        teacher.setUser(user);
        var examSheet1 = examSheetRepository.create();
        examSheet1.setNumber("1");
        examSheet1.setTeacher(teacher);
        var examSheet2 = examSheetRepository.create();
        examSheet2.setNumber("2");
        examSheet2.setTeacher(teacher);
        teacher.setExamSheets(Set.of(examSheet1, examSheet2));
        teacherRepository.save(teacher);
        List<UUID> foundIds = examSheetRepository.getExamSheetsByTeacher(teacher)
                .stream().sorted(Comparator.comparing(ExamSheet::getNumber)).map(ExamSheet::getId).collect(Collectors.toList());
        List<UUID> createdIds = List.of(examSheet1.getId(), examSheet2.getId());
        teacherRepository.delete(teacher);
        assertEquals(createdIds, foundIds, "Not equals");
    }
}
