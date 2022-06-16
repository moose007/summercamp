package com.mcc.summercamp.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;

@Data
public class SignUpRequest {
    private String displayName;

    @Email
    private String email;

    @ToString.Exclude private String password;
}
