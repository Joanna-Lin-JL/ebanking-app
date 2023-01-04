package com.app.ebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebanking.repository.AccountRepository;
import com.app.ebanking.repository.TransactionRepository;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
  @Autowired
  TransactionRepository transactionrepository;
  AccountRepository accountRepository;

  @GetMapping("/")
  public String greeting() {
    return "Hello world! ";
  }

  @PostMapping("/create")
  public void createTransaction(String amount, String description, String iban) {

  }

  @PutMapping("/{id}")
  public void updateTransaction() {

  }

  @DeleteMapping("/{id}")
  public void deleteTransaction() {

  }

}
