package com.mcc.summercamp.service.impl;

import com.mcc.summercamp.dto.SignUpRequest;
import com.mcc.summercamp.exception.BadRequestException;
import com.mcc.summercamp.exception.ResourceNotFoundException;
import com.mcc.summercamp.repository.SupportedRoles;
import com.mcc.summercamp.repository.UserRepository;
import com.mcc.summercamp.repository.entity.User;
import com.mcc.summercamp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Transactional
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  private PasswordEncoder passwordEncoder;

  private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User getUserById(Long id) {
    Optional<User> user = userRepository.findById(id);

    logger.info("Getting user by id: {}", id);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new ResourceNotFoundException("User", "id", id);
    }
  }

  @Override
  public User create(SignUpRequest signUpRequest) {
    logger.info("Got request to create user={}", signUpRequest.toString());
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new BadRequestException("Email address already in use.");
    }

    User user = new User();
    user.setDisplayName(signUpRequest.getDisplayName());
    user.setEmail(signUpRequest.getEmail());
    user.setPassword(signUpRequest.getPassword());

    user.setRoles(Arrays.asList(SupportedRoles.ROLE_USER.name()));
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User result = userRepository.save(user);

    return result;
  }
}
