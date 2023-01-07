package com.app.ebanking.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.ebanking.model.Client;
import com.app.ebanking.repository.ClientRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private ClientRepository clientRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Client client = clientRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));

    return client;
  }

}
