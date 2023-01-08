package com.app.ebanking.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebanking.generator.ResponseHandler;
import com.app.ebanking.model.Client;
import com.app.ebanking.repository.ClientRepository;

/** Contains endpoints relating to the client */
@RestController
@RequestMapping("/api/client")
public class ClientController {
  @Autowired
  private ClientRepository clientRepository;

  /** Tester endpoint to greet the client */
  @GetMapping("/")
  public String greeting() {
    return "Hello world! ";
  }

  /**
   * Endpoint to get information about a client
   * 
   * @param id the client's uuid given in the request's param
   */
  @GetMapping("/one")
  public ResponseEntity<Object> getOneClient(@RequestParam String id) {
    try {
      UUID uuid = UUID.fromString(id);
      Optional<Client> client = clientRepository.findById(uuid);
      if (!client.isPresent())
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      else
        return ResponseHandler.clientShort(HttpStatus.OK, client.get());
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Endpoint to delete a client's registration
   * 
   * @param id the client's uuid given in the request's param
   */
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
