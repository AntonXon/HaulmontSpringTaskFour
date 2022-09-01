package com.company.haulmontspringtask.repository;


import com.company.haulmontspringtask.BaseTest;
import com.company.haulmontspringtask.entity.Teacher;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TeacherRepositoryTest extends BaseTest {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FetchPlans fetchPlans;

    @Test
    void findById() {
        //given
        String firstName = "Ann";
        String lastName = "Petrov";
        var user = userRepository.create();
        user.setUsername("Test_findById");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        var teacher = teacherRepository.create();
        teacher.setUser(user);
        teacher = teacherRepository.save(teacher);
        entitiesToDelete.add(teacher);
        var createdUUID = teacher.getId();
        var fetchPlan = fetchPlans.builder(Teacher.class)
                .addFetchPlan(FetchPlan.BASE)
                .add("user")
                .build();
        //when
        var foundTeacher = teacherRepository.getById(teacher.getId(), fetchPlan);
        var foundUUID = foundTeacher.getId();
        //then
        assertEquals(createdUUID, foundUUID, "Not equals");
        assertNotNull(foundTeacher.getUser(), "User is null");
        assertEquals(firstName, foundTeacher.getUser().getFirstName(), "Not equals");
        assertEquals(lastName, foundTeacher.getUser().getLastName(), "Not equals");
    }

    @Test
    void findTeacherByName() {
        //given
        String firstName = "Ann";
        String lastName = "Petrov";
        var user = userRepository.create();
        user.setUsername("Test_findTeacherByName");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        var teacher = teacherRepository.create();
        teacher.setUser(user);
        teacher = teacherRepository.save(teacher);
        entitiesToDelete.add(teacher);
        List<UUID> createdUUID = Stream.of(teacher.getId()).collect(Collectors.toList());
        //when
        List<Teacher> foundTeacher = teacherRepository.findTeachersByName(firstName, lastName);
        List<UUID> foundUUID = foundTeacher.stream().map(Teacher::getId).collect(Collectors.toList());
        //then
        assertEquals(createdUUID, foundUUID, "Not equals");
    }
}
