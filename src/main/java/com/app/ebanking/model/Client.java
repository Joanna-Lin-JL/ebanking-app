package com.app.ebanking.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

// import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client implements UserDetails {

  @Id
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  @OneToMany(mappedBy = "client")
  @Column(name = "accounts")
  private List<Account> accounts;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  public Client() {
    this.accounts = new ArrayList<>();
  }

  public Client(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public List<Account> getAccount() {
    return accounts;
  }

  public void addAccount(Account account) {
    this.accounts.add(account);
  }

  public UUID getID() {
    return this.id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

}