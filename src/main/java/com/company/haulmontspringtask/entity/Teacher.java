package com.company.haulmontspringtask.entity;


import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@JmixEntity
@Entity
@Table(name = "TEACHER")
public class Teacher {
    @Id
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Set<ExamSheet> examSheets;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ExamSheet> getExamSheets() {
        return examSheets;
    }

    public void setExamSheets(Set<ExamSheet> examSheets) {
        this.examSheets = examSheets;
    }
}
