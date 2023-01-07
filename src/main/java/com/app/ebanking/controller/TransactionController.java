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

/** Contains endpoints relating to transactions. Requires authentication. */
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
  @Autowired
  TransactionRepository transactionRepository;

  @Autowired
  AccountRepository accountRepository;

  /**
   * Endpoint to get information about a transaction
   * 
   * @param id the transaction's uuid given in the request's param
   */
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

  /**
   * Endpoint to make a transaction associating with a client's account
   * 
   * @param account_iban given in the request's param
   * @return the created transaction serialized with http status
   */
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
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // TODO: Make transactions actually change the account balance
  /**
   * Endpoint to update the transaction information
   * 
   * @param id              the transaction's uuid given in the request's param
   * @param transaction_mod modified transaction information read from JSON. Not
   *                        all fields are required, only the updated ones.
   */
  @PutMapping("/update")
  public ResponseEntity<Object> updateTransaction(@RequestParam String id, @RequestBody Transaction transaction_mod) {
    try {
      UUID uuid = UUID.fromString(id);
      Optional<Transaction> transaction_opt = transactionRepository.findById(uuid);
      if (transaction_opt.isEmpty())
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

      Transaction transaction = transaction_opt.get();
      if (transaction_mod.getDescription() != null) {
        transaction.setDescription(transaction_mod.getDescription());
      }
      if (transaction_mod.getAmount() != null) {
        transaction.setAmount(transaction_mod.getAmount());
      }
      return ResponseHandler.transactionShort(HttpStatus.OK, transaction);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  /**
   * Endpoint to delete a transaction
   * 
   * @param id the transaction's uuid given in the request's param
   */
  @DeleteMapping("/delete")
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
