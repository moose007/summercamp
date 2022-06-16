package com.mcc.summercamp.security;

import com.mcc.summercamp.repository.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

  private static final long serialVersionUID = 1L;
  private Long id;
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;
  private User user;

  public UserPrincipal(
      Long id,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      User user) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.user = user;
  }

  public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities = getAuthorities(user.getRoles());
    return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), authorities, user);
  }

  public static UserPrincipal create(User user, Map<String, Object> attributes) {
    UserPrincipal userPrincipal = UserPrincipal.create(user);
    userPrincipal.setAttributes(attributes);
    return userPrincipal;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public User getUser() {
    return user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  private static List<GrantedAuthority> getAuthorities(final List<String> roles) {
    return roles.stream().map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
  }
}
