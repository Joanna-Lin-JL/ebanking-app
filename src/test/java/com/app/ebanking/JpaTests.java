package com.app.ebanking;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.app.ebanking.model.Client;
import com.app.ebanking.repository.AccountRepository;
import com.app.ebanking.repository.ClientRepository;
import com.app.ebanking.repository.TransactionRepository;

@DataJpaTest
class JpaTests {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  private static Logger logger = LoggerFactory.getLogger(EbankingApplicationTests.class);

  @Test
  void contextLoads() {
  }

  @Test
  public void emptyRepositories() {
    assert (clientRepository.findAll().isEmpty());
    assert (accountRepository.findAll().isEmpty());
    assert (transactionRepository.findAll().isEmpty());
  }

  @Test
  public void clientTest() {
    try {
      Client client = new Client("username1", "password1");
      entityManager.persist(client);

      // Check client is stored correctly and found correctly with repository
      Client byUsername = clientRepository.findByUsername("username1").get();
      assertEquals(byUsername.getID().toString(), client.getID().toString());
      assertArrayEquals(byUsername.getAccount().toArray(), (new Object[0]));
      assertNotEquals(byUsername.getPassword(), null);

      // Check finding client through username is the same as finding through uuid
      Client byId = clientRepository.findById(client.getID()).get();
      assertEquals(byId.getID(), byUsername.getID());

      // Check clients are deleted correctly
      entityManager.persist(new Client("willdelete", "password2"));
      clientRepository.deleteByUsername("willdelete");
      assertThrows(NoSuchElementException.class, () -> {
        clientRepository.findByUsername("willdelete").get();
      });

    } catch (NoSuchElementException e) {
      logger.error("client not stored: {}", e.getMessage());
    } catch (Exception e) {
      logger.error("other errors: {}", e.getMessage());
    }
  }

  @Test
  public void accountTests() {
    try {
      Client client = new Client("username1", "password1");
      entityManager.persist(client);
      String client_id = clientRepository.findByUsername("username1").get().getID().toString();

    } catch (NoSuchElementException e) {
      logger.error("client not stored: {}", e.getMessage());
    } catch (Exception e) {
      logger.error("other errors: {}", e.getMessage());
    }
  }

}
