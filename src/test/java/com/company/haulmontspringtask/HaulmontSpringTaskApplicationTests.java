package com.company.haulmontspringtask;

import com.company.haulmontspringtask.entity.User;
import com.company.haulmontspringtask.security.DatabaseUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HaulmontSpringTaskApplicationTests {
    @Autowired
    DatabaseUserRepository databaseUserRepository;

    @Test
    void checkUser() {
        User systemUser = databaseUserRepository.getSystemUser();
        assertEquals(systemUser.getUsername(), "system", "System user is not correct");
    }

}
