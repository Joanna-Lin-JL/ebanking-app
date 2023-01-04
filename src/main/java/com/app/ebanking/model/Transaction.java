package com.app.ebanking.model;

import java.time.*;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

  @Id
  @GeneratedValue
  private UUID uuid;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "amount")
  private String amount; // Consider class MonetaryAmount for wider usage

  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_iban")
  private Account account;

  public Transaction(String amount, String description, Account account) {
    this.uuid = UUID.randomUUID();
    this.date = LocalDate.now();
    this.amount = amount;
    this.description = description;
    this.account = account;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  // TODO: set getter and setter for the desired

}