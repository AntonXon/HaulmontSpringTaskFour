package com.company.haulmontspringtask.repository;

import com.company.haulmontspringtask.entity.Teacher;
import io.jmix.core.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;
import io.jmix.core.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface TeacherRepository extends JmixDataRepository<Teacher, UUID> {
    @Query("select t from Teacher t where t.user.firstName = :firstName and t.user.lastName = :lastName")
    List<Teacher> findTeachersByName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    Teacher getById(UUID id, @Nullable FetchPlan fetchPlan);
}
