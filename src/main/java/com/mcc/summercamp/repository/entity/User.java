package com.mcc.summercamp.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(
    name = "USERT",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"email"})
    })
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "user_seq", initialValue = 1000, allocationSize = 100)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  private Long id;

  @Column(nullable = false)
  private String displayName;

  @Email
  @Column(nullable = false)
  private String email;

  private String profileImageUrl;

  @JsonIgnore private String password;

  @ElementCollection
  List<String> roles;

}
