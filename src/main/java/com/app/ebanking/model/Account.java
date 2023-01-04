package com.app.ebanking.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

  @Id
  private String iban; // Consider iban4j with https://github.com/arturmkrtchyan/iban4j

  @Column(name = "currency")
  private Currency currency;

  @OneToMany(mappedBy = "account")
  private List<Transaction> transactions;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_uuid")
  private Client client;

  public Account(Client client, String iban, Currency currency) {
    this.client = client;
    this.iban = iban;
    this.currency = currency;
    this.transactions = new ArrayList<>();
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

}