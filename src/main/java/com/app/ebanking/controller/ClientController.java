package com.app.ebanking.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebanking.model.Client;
import com.app.ebanking.repository.ClientRepository;

@RestController
@RequestMapping("/api/client")
public class ClientController {
  @Autowired
  private ClientRepository clientRepository;

  @GetMapping("/one")
  public ResponseEntity<Client> getOneClient(String uuid) {
    Optional<Client> client = clientRepository.findById(uuid);
    if (client.isEmpty())
      return ResponseEntity.notFound().build();
    else
      return ResponseEntity.ok(client.get());
  }

  @PostMapping("/create")
  public ResponseEntity<Client> createClient() {
    Client new_client = clientRepository.save(new Client());
    return ResponseEntity.ok(new_client);
  }

  @PutMapping("/update")
  public void updateClient() {

  }

  @DeleteMapping("/{id}")
  public void deleteClient() {

  }

}
