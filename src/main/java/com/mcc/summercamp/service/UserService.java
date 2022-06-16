package com.mcc.summercamp.service;

import com.mcc.summercamp.dto.SignUpRequest;
import com.mcc.summercamp.repository.entity.User;

public interface UserService {
  User getUserById(Long id);
  User create(SignUpRequest signUpRequest);
}
