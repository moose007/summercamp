package com.mcc.summercamp.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.mcc.summercamp.repository")
@PropertySource("classpath:repository.datasource.properties")
@EntityScan(basePackages = {"com.mcc.summercamp.repository.entity"})
@EnableJpaAuditing
public class JPAPersistenceConfig {}
