package com.app.ebanking.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebanking.model.Account;
import com.app.ebanking.model.Client;
import com.app.ebanking.repository.AccountRepository;

@RestController
@RequestMapping("/api/account")
public class AccountController {
  @Autowired
  AccountRepository accountRepository;
  ClientController clientController;

  @GetMapping("/")
  public String greeting() {
    return "Hello world! ";
  }

  // Consider get account from client
  @GetMapping("/one")
  public ResponseEntity<Account> getOneAccount(String iban) {
    Optional<Account> account = accountRepository.findById(iban);
    if (account.isEmpty())
      return ResponseEntity.notFound().build();
    else
      return ResponseEntity.ok(account.get());
  }

  @GetMapping("/client")
  public ResponseEntity<Client> getClient(String iban) {
    Optional<Account> account = accountRepository.findById(iban);
    if (account.isEmpty())
      return ResponseEntity.notFound().build();
    else {
      return ResponseEntity.ok(account.get().getClient());
    }
  }

  @PostMapping("/create")
  public void createAccount() {
  }

  @PutMapping("/{id}")
  public void updateAccount() {

  }

  @DeleteMapping("/{id}")
  public void deleteAccount() {

  }

}
