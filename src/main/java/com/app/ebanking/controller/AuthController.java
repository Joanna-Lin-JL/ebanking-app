package com.app.ebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ebanking.generator.ResponseHandler;
import com.app.ebanking.model.Client;
import com.app.ebanking.repository.ClientRepository;
import com.app.ebanking.security.config.JwtTokenUtils;
import com.app.ebanking.security.payload.AuthRequest;
import com.app.ebanking.security.payload.AuthResponse;
import com.app.ebanking.security.payload.MessageResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenUtils tokenUtils;

  @PostMapping("/signup")
  public ResponseEntity<Object> signup(@RequestBody AuthRequest authRequest) {
    try {
      if (clientRepository.existsByUsername(authRequest.getUsername())) {
        return new ResponseEntity<>(new MessageResponse("Error: Username already exists! "), HttpStatus.BAD_REQUEST);
      }

      Client client = new Client(authRequest.getUsername(), passwordEncoder.encode(authRequest.getPassword()));
      Client new_client = clientRepository.save(client);

      return ResponseHandler.clientShort(HttpStatus.CREATED, new_client);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<Object> signin(@RequestBody AuthRequest authRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenUtils.generateJwtToken(authentication);

    Client userDetails = (Client) authentication.getPrincipal();
    System.out.println("ran here");
    return new ResponseEntity<>(new AuthResponse(jwt,
        userDetails.getID(),
        userDetails.getUsername()), HttpStatus.OK);
  }
}
