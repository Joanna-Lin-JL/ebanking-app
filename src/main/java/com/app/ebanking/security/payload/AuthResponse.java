package com.app.ebanking.security.payload;

import java.util.UUID;

public class AuthResponse {
  private String token;
  private String type = "Bearer";
  private UUID id;
  private String username;

  /** Object for information after authentication */
  public AuthResponse(String accessToken, UUID id, String username) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public String getUsername() {
    return username;
  }

  public String getId() {
    return id.toString();
  }

  public String getTokenType() {
    return type;
  }
}
