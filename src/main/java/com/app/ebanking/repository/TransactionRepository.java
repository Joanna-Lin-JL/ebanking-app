package com.app.ebanking.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.ebanking.model.Transaction;
import java.util.UUID;

/** The transaction JPA repository interface */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}