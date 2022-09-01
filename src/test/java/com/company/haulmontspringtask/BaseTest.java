package com.company.haulmontspringtask;


import com.company.haulmontspringtask.mongoRepository.SignRepository;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;

@SpringBootTest
public class BaseTest {
    public ArrayList<Object> entitiesToDelete = new ArrayList<>();

    @Autowired
    SystemAuthenticator systemAuthenticator;
    @Autowired
    DataManager dataManager;

    @BeforeEach
    void setUp() {
        systemAuthenticator.begin();
    }

    @AfterEach
    void tearDown() {
        dataManager.remove(entitiesToDelete);
        entitiesToDelete.clear();
        //можно сюда убрать удаление объектов
        systemAuthenticator.end();
    }
}
