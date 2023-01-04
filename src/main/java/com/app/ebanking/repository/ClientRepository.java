package com.app.ebanking.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.ebanking.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}