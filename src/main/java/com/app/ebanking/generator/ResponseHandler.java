package com.app.ebanking.generator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.app.ebanking.model.*;

public class ResponseHandler {
  public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("message", message);
    map.put("status", status.value());
    map.put("data", responseObj);

    return new ResponseEntity<Object>(map, status);
  }

  public static ResponseEntity<Object> clientShort(HttpStatus status, Client client) {
    Map<String, Object> map = new HashMap<>();
    Map<String, Object> accountMap = new HashMap<>();
    map.put("uuid", client.getID());
    client.getAccount().forEach((acc) -> {
      accountMap.put("iban", acc.getIban());
      accountMap.put("currency", acc.getCurrency());
    });
    map.put("accounts", accountMap);
    map.put("username", client.getUsername());
    return new ResponseEntity<>(map, status);
  }

  public static ResponseEntity<Object> accountShort(HttpStatus status, Account account) {
    Map<String, Object> map = new HashMap<>();
    map.put("username", account.getClient().getUsername());
    map.put("iban", account.getIban());
    map.put("currency", account.getCurrency());
    map.put("client_uuid", account.getClient().getID());
    return new ResponseEntity<>(map, status);
  }

  public static ResponseEntity<Object> transactionShort(HttpStatus status, Transaction transaction) {
    Map<String, Object> map = new HashMap<>();

    map.put("username", transaction.getAccount().getClient().getUsername());
    map.put("transaction_uuid", transaction.getID());
    map.put("date", transaction.getDate());
    map.put("amount", transaction.getAmount());
    map.put("description", transaction.getDescription());
    map.put("client_uuid", transaction.getAccount().getClient().getID());
    map.put("account_iban", transaction.getAccount().getIban());
    return new ResponseEntity<>(map, status);
  }
}
