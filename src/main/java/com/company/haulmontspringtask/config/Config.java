package com.company.haulmontspringtask.config;

import io.jmix.core.repository.EnableJmixDataRepositories;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJmixDataRepositories(basePackages = "com.company.haulmontspringtask.repository")
@EnableMongoRepositories(basePackages = "com.company.haulmontspringtask.mongoRepository")
class Config {
}
