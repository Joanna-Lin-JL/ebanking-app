package com.app.ebanking.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebanking.model.Client;
import com.app.ebanking.repository.ClientRepository;

@RestController
@RequestMapping("/api/client")
public class ClientController {
  @Autowired
  private ClientRepository clientRepository;

  @GetMapping("/")
  public String greeting() {
    return "Hello world! ";
  }

  @GetMapping("/one")
  public ResponseEntity<Client> getOneClient(@RequestParam String id) {
    try {
      UUID uuid = UUID.fromString(id);
      Optional<Client> client = clientRepository.findById(uuid);
      if (client.isEmpty())
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      else
        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<Client> createClient() {
    try {
      Client new_client = clientRepository.save(new Client());
      return new ResponseEntity<>(new_client, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/account")
  public void addAccount() {

  }

  @DeleteMapping("/delete")
  public ResponseEntity<Client> deleteClient(@RequestParam String id) {
    try {
      UUID uuid = UUID.fromString(id);
      if (clientRepository.existsById(uuid)) {
        clientRepository.deleteById(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
