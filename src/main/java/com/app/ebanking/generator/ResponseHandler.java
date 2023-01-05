package com.app.ebanking.generator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.app.ebanking.model.Account;
import com.app.ebanking.model.Client;

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
    map.put("uuid", client.getID());
    return new ResponseEntity<>(map, status);
  }

  public static ResponseEntity<Object> accountShort(HttpStatus status, Account account) {
    Map<String, Object> map = new HashMap<>();
    Map<String, Object> client_map = new HashMap<>();
    map.put("iban", account.getIban());
    map.put("currency", account.getCurrency());
    client_map.put("uuid", account.getClient().getID());
    map.put("client", client_map);
    return new ResponseEntity<>(map, status);
  }
}
