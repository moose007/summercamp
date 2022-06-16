package com.mcc.summercamp.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JPAPersistenceConfig.class})
public class RepositoryConfigurationAggregate {}
