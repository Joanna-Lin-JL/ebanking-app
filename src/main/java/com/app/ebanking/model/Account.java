package com.app.ebanking.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(generator = "IBAN_GEN")
  @GenericGenerator(name = "IBAN_GEN", strategy = "com.app.ebanking.generator.IbanGenerator")
  private String iban;

  @Column(name = "currency")
  private String currency;

  @OneToMany(mappedBy = "account")
  private List<Transaction> transactions;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

  public Account(Client client, String currency) {
    this.client = client;
    this.currency = currency;
    this.transactions = new ArrayList<>();
  }

  public Account(String currency) {
    this.currency = currency;
    this.transactions = new ArrayList<>();
  }

  public Account() {
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getIban() {
    return this.iban;
  }

  public Client getClient() {
    return client;
  }

  public String getCurrency() {
    return this.currency;
  }

  public List<Transaction> getTransaction() {
    return transactions;
  }

}