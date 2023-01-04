package com.app.ebanking.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.ebanking.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
