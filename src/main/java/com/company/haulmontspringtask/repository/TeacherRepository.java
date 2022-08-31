package com.company.haulmontspringtask.repository;

import com.company.haulmontspringtask.entity.Teacher;
import io.jmix.core.repository.JmixDataRepository;
import io.jmix.core.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TeacherRepository extends JmixDataRepository<Teacher, UUID> {
    @Query("select t from Teacher t where t.user.firstName = :firstName and t.user.lastName = :lastName")
    List<Teacher> findTeachersByName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("select t from Teacher t JOIN FETCH t.user where t.id = :id")
    List<Teacher> findByTeacherId(@Param("id") UUID id);
}
