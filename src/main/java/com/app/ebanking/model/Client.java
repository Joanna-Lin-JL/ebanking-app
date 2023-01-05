package com.app.ebanking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

// import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {

  @Id
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  @OneToMany(mappedBy = "client")
  @Column(name = "accounts")
  private List<Account> accounts;

  public Client() {
    this.accounts = new ArrayList<>();
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

}