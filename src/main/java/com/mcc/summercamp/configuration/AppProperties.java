package com.mcc.summercamp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
  private final Auth auth = new Auth();

  public AppProperties() {}

  @Data
  public static class Auth {
    private String tokenSecret;
    private long tokenExpirationMsec;
  }
}
