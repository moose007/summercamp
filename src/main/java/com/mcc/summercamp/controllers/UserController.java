package com.mcc.summercamp.controllers;

import com.mcc.summercamp.repository.entity.User;
import com.mcc.summercamp.security.CurrentUser;
import com.mcc.summercamp.security.UserPrincipal;
import com.mcc.summercamp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/users")
public class UserController {

  private UserService userService;

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  @ResponseBody
  @PreAuthorize("hasRole('USER')")
  public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
    return userService.getUserById(userPrincipal.getId());
  }
}
