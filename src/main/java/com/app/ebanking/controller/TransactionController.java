package com.app.ebanking.controller;

import java.util.Optional;
import java.util.UUID;

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

import com.app.ebanking.repository.AccountRepository;
import com.app.ebanking.repository.TransactionRepository;
import com.app.ebanking.generator.ResponseHandler;
import com.app.ebanking.model.Account;
import com.app.ebanking.model.Transaction;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  AccountRepository accountRepository;

  @GetMapping("/")
  public String greeting() {
    return "Hello world! ";
  }

  @GetMapping("/one")
  public ResponseEntity<Object> getOneTransaction(@RequestParam String id) {
    try {
      UUID uuid = UUID.fromString(id);
      Optional<Transaction> transaction = transactionRepository.findById(uuid);
      if (transaction.isEmpty())
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      else
        return ResponseHandler.transactionShort(HttpStatus.OK, transaction.get());
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<Object> createTransaction(@RequestParam String account_iban,
      @RequestBody Transaction transaction) {
    try {
      Optional<Account> account = accountRepository.findById(account_iban);
      if (account.isEmpty())
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

      Transaction new_transaction = transactionRepository
          .save(new Transaction(transaction.getAmount(), transaction.getDescription(), account.get()));
      return ResponseHandler.transactionShort(HttpStatus.CREATED, new_transaction);
    } catch (Exception e) {
      System.out.println(e);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public void updateTransaction() {

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteTransaction(@RequestParam String id) {
    try {
      UUID uuid = UUID.fromString(id);
      if (transactionRepository.existsById(uuid)) {
        transactionRepository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
