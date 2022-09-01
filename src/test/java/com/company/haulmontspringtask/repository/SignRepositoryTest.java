package com.company.haulmontspringtask.repository;

import com.company.haulmontspringtask.BaseTest;
import com.company.haulmontspringtask.entity.ExamSheet;
import com.company.haulmontspringtask.entity.Sign;
import com.company.haulmontspringtask.mongoRepository.SignRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class SignRepositoryTest extends BaseTest {
    @Autowired
    SignRepository signRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    ExamSheetRepository examSheetRepository;

    @Test
    public void createSign() {
        //given
        var user = userRepository.create();
        user.setUsername("TEST");
        var teacher = teacherRepository.create();
        teacher.setUser(user);
        Random random = new Random();
        String number = ExamSheet.class.getName() + random.nextInt();
        var examSheet = examSheetRepository.create();
        examSheet.setNumber(number);
        examSheet.setTeacher(teacher);
        teacherRepository.save(teacher);
        entitiesToDelete.add(teacher);
        //when
        Sign createdSign = signRepository.createSign(teacher, examSheet);
        Sign foundSign = signRepository.findSignByExamSheetId(examSheet.getId().toString());
        //then
        assertThrows(RuntimeException.class, () -> {
            signRepository.createSign(teacher, examSheet);
        }, "Can create two signs for teacher/sheet");
        signRepository.delete(createdSign);
        assertEquals(createdSign.getId(), foundSign.getId(), "Not equals");
    }

    @Test
    void findSignByExamSheetId() {
        //given
        var user = userRepository.create();
        user.setUsername("TEST");
        var teacher = teacherRepository.create();
        teacher.setUser(user);
        Random random = new Random();
        String number = ExamSheet.class.getName() + random.nextInt();
        var examSheet = examSheetRepository.create();
        examSheet.setNumber(number);
        examSheet.setTeacher(teacher);
        teacherRepository.save(teacher);
        entitiesToDelete.add(teacher);
        Sign createdSign = signRepository.createSign(teacher, examSheet);
        //when
        Sign foundSign = signRepository.findSignByExamSheetId(examSheet.getId().toString());
        signRepository.delete(createdSign);
        //then
        assertNotNull(foundSign, "Sign not found");
        assertEquals(createdSign.getId(), foundSign.getId(), "Not equals");
    }

    @Test
    void findSignsByTeacherId() {
        //given
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
        var examSheet2 = examSheetRepository.create();
        examSheet2.setNumber(number2);
        examSheet2.setTeacher(teacher);
        teacherRepository.save(teacher);
        entitiesToDelete.add(teacher);
        Sign sign1 = signRepository.createSign(teacher, examSheet1);
        Sign sign2 = signRepository.createSign(teacher, examSheet2);
        List<String> createdSigns = Stream.of(sign1, sign2)
                .sorted(Comparator.comparing(Sign::getId))
                .map(Sign::getId)
                .collect(Collectors.toList());
        //when
        List<String> foundSigns = signRepository.findSignsByTeacherId(teacher.getId().toString())
                .stream().sorted(Comparator.comparing(Sign::getId))
                .map(Sign::getId).collect(Collectors.toList());
        signRepository.delete(sign1);
        signRepository.delete(sign2);
        //then
        assertEquals(createdSigns, foundSigns, "Not equals");
    }

    @Test
    void findSignsBySignDateBetween() {
        //given
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
        var examSheet2 = examSheetRepository.create();
        examSheet2.setNumber(number2);
        examSheet2.setTeacher(teacher);
        teacherRepository.save(teacher);
        entitiesToDelete.add(teacher);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 5, 1, 12, 0);
        Sign sign1 = new Sign();
        sign1.setTeacherId(teacher.getId().toString());
        sign1.setExamSheetId(examSheet1.getId().toString());
        sign1.setSignDate(cal.getTime());
        sign1 = signRepository.save(sign1);
        cal.set(2022, 6, 1, 12, 0);
        Sign sign2 = new Sign();
        sign2.setTeacherId(teacher.getId().toString());
        sign2.setExamSheetId(examSheet2.getId().toString());
        sign2.setSignDate(cal.getTime());
        signRepository.save(sign2);
        List<String> createdSigns = Stream.of(sign1)
                .map(Sign::getId)
                .collect(Collectors.toList());
        Calendar calFrom = Calendar.getInstance();
        calFrom.set(2022, 5, 1, 11, 0);
        Calendar calTo = Calendar.getInstance();
        calTo.set(2022, 6, 1, 11, 0);
        //when
        List<String> foundSigns = signRepository.findSignsBySignDateBetween(calFrom.getTime(), calTo.getTime())
                .stream().sorted(Comparator.comparing(Sign::getId))
                .map(Sign::getId).collect(Collectors.toList());
        signRepository.delete(sign1);
        signRepository.delete(sign2);
        //then
        assertEquals(createdSigns, foundSigns, "Not equals");
    }
}
