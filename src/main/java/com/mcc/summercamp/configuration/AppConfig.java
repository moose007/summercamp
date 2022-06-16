package com.mcc.summercamp.configuration;

import com.mcc.summercamp.repository.RepositoryConfigurationAggregate;
import com.mcc.summercamp.repository.UserRepository;
import com.mcc.summercamp.security.WebSecurityConfig;
import com.mcc.summercamp.service.UserService;
import com.mcc.summercamp.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableConfigurationProperties(AppProperties.class)
@Configuration
@Import({
        RepositoryConfigurationAggregate.class,
        WebSecurityConfig.class,
})
public class AppConfig {

  Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return new UserServiceImpl(userRepository, passwordEncoder);
  }

}
