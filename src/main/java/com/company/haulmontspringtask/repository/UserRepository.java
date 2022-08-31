package com.company.haulmontspringtask.repository;

import com.company.haulmontspringtask.entity.User;
import io.jmix.core.repository.JmixDataRepository;

import java.util.UUID;

public interface UserRepository extends JmixDataRepository<User, UUID> {
}
