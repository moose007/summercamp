package com.mcc.summercamp.controllers;

import com.mcc.summercamp.dto.AuthResponse;
import com.mcc.summercamp.dto.LoginRequest;
import com.mcc.summercamp.dto.SignUpRequest;
import com.mcc.summercamp.repository.entity.User;
import com.mcc.summercamp.security.TokenProvider;
import com.mcc.summercamp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.text.MessageFormat;

@RestController
@RequestMapping("/auth")
public class LocalAuthController {

  private AuthenticationManager authenticationManager;

  private UserService service;

  private TokenProvider tokenProvider;

  private Logger logger = LoggerFactory.getLogger(LocalAuthController.class);

  @Autowired
  public LocalAuthController(
      AuthenticationManager authenticationManager,
      UserService service,
      TokenProvider tokenProvider) {
    this.authenticationManager = authenticationManager;
    this.service = service;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = tokenProvider.createToken(authentication);
    return ResponseEntity.ok(new AuthResponse(token));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

    User result = service.create(signUpRequest);
    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/user/me")
            .buildAndExpand(result.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(MessageFormat.format("welcome {0}", signUpRequest.getDisplayName()));
  }
}
