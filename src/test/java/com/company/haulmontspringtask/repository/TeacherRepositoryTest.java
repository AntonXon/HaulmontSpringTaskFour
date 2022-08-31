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

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TeacherRepositoryTest {
    @Autowired
    protected DataManager dataManager;
    @Autowired
    TeacherRepository teacherRepository;
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
    void findById() {
        String firstName = "Ann";
        String lastName = "Petrov";
        var user = dataManager.create(User.class);
        user.setUsername("Test_findById");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        var teacher = dataManager.create(Teacher.class);
        teacher.setUser(user);
        teacher = dataManager.save(teacher);
        var createdUUID = List.of(teacher.getId());
        var foundTeacher = teacherRepository.findByTeacherId(teacher.getId());
        var foundUUID = foundTeacher.stream().map(Teacher::getId).collect(Collectors.toList());
        dataManager.remove(teacher);
        assertEquals(createdUUID, foundUUID, "Not equals");
        assertNotNull(foundTeacher.get(0).getUser(), "User is null");
        assertEquals(firstName, foundTeacher.get(0).getUser().getFirstName(), "Not equals");
        assertEquals(lastName, foundTeacher.get(0).getUser().getLastName(), "Not equals");
    }

    @Test
    void findTeacherByName() {
        String firstName = "Ann";
        String lastName = "Petrov";
        var user = dataManager.create(User.class);
        user.setUsername("Test_findTeacherByName");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        var teacher = dataManager.create(Teacher.class);
        teacher.setUser(user);
        teacher = dataManager.save(teacher);
        List<UUID> createdUUID = Stream.of(teacher.getId()).collect(Collectors.toList());
        List<Teacher> foundTeacher = teacherRepository.findTeachersByName(firstName, lastName);
        List<UUID> foundUUID = foundTeacher.stream().map(Teacher::getId).collect(Collectors.toList());
        dataManager.remove(teacher);
        assertEquals(createdUUID, foundUUID, "Not equals");
    }
}
