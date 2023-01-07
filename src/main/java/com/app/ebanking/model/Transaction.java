package com.app.ebanking.model;

import java.time.*;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

  @Id
  @GeneratedValue
  @UuidGenerator
  private UUID id;

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
    this.date = LocalDate.now();
    this.amount = amount;
    this.description = description;
    this.account = account;
  }

  public Transaction() {
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public Account getAccount() {
    return account;
  }

  public String getDescription() {
    return description;
  }

  public String getAmount() {
    return amount;
  }

  public LocalDate getDate() {
    return date;
  }

  public UUID getID() {
    return id;
  }

}