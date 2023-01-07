package com.app.ebanking.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.ebanking.model.Client;
import java.util.UUID;
import java.util.Optional;

/** The client JPA repository interface */
@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
  Boolean existsByUsername(String username);

  Optional<Client> findByUsername(String username);
}