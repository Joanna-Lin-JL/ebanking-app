package com.app.ebanking.security.payload;

/** Object of username and password of the client */
public class AuthRequest {
  private String username;

  private String password;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

}
