package com.app.ebanking.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

import com.app.ebanking.generator.ResponseHandler;
import com.app.ebanking.model.Account;
import com.app.ebanking.model.Client;
import com.app.ebanking.repository.AccountRepository;
import com.app.ebanking.repository.ClientRepository;

@RestController
@RequestMapping("/api/account")
public class AccountController {
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private ClientRepository clientRepository;

  @GetMapping("/")
  public String greeting() {
    return "Hello world! ";
  }

  // Consider get account from client
  @GetMapping("/one")
  public ResponseEntity<Object> getOneAccount(@RequestParam String iban) {
    Optional<Account> account = accountRepository.findById(iban);
    if (account.isEmpty())
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    else
      return ResponseHandler.accountShort(HttpStatus.OK, account.get());
  }

  @PostMapping("/create")
  public ResponseEntity<Object> createAccount(@RequestParam String client_uuid, @RequestBody Account account) {
    try {
      UUID uuid = UUID.fromString(client_uuid);
      Optional<Client> client = clientRepository.findById(uuid);

      if (client.isEmpty())
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

      Account new_account = accountRepository.save(new Account(client.get(), account.getCurrency()));
      return ResponseHandler.accountShort(HttpStatus.CREATED, new_account);
    } catch (Exception e) {
      System.out.println(e);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public void updateAccount() {

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteAccount(@RequestParam String iban) {
    try {
      if (accountRepository.existsById(iban)) {
        accountRepository.deleteById(iban);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
