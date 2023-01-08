package com.app.ebanking;

import org.iban4j.Iban;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.app.ebanking.controller.AccountController;
import com.app.ebanking.controller.ClientController;
import com.app.ebanking.controller.TransactionController;
import com.app.ebanking.model.Account;
import com.app.ebanking.model.Client;
import com.app.ebanking.model.Transaction;
import com.app.ebanking.repository.AccountRepository;
import com.app.ebanking.repository.ClientRepository;
import com.app.ebanking.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ContextConfiguration
@WebMvcTest(controllers = TransactionController.class)
@WithMockUser
class TransactionControllerTests {

  @MockBean
  private ClientRepository clientRepository;

  @InjectMocks
  ClientController clientController;

  @MockBean
  private AccountRepository accountRepository;

  @InjectMocks
  AccountController accountController;

  @MockBean
  private TransactionRepository transactionRepository;

  @InjectMocks
  TransactionController transactionController;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void contextLoads() {
  }

  @Test
  public void createTransactionTest() throws Exception {
    String iban = Iban.random().toFormattedString();
    UUID client_uuid = UUID.randomUUID();
    UUID transaction_uuid = UUID.randomUUID();
    Client client = new Client(client_uuid, "username1", "password1");
    Account account = new Account(iban, client, "USD");
    Transaction transaction = new Transaction(transaction_uuid, "USD100", "Buy a dog house", account);

    when(accountRepository.findById(iban)).thenReturn(Optional.of(account));
    when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

    Map<String, Object> input = new HashMap<>();
    input.put("amount", "USD100");
    input.put("description", "Buy a dog house");

    mockMvc
        .perform(
            post("/api/transaction/create").with(csrf())
                .param("account_iban", iban)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value("username1"))
        .andExpect(jsonPath("$.transaction_uuid").value(transaction_uuid.toString()))
        .andExpect(jsonPath("$.date").exists())
        .andExpect(jsonPath("$.amount").value("USD100"))
        .andExpect(jsonPath("$.description").value("Buy a dog house"))
        .andExpect(jsonPath("$.client_uuid").value(client_uuid.toString()))
        .andExpect(jsonPath("$.account_iban").value(iban))
        .andDo(print());
  }

  @Test
  public void getTransactionTest() throws Exception {
    String iban = Iban.random().toFormattedString();
    UUID client_uuid = UUID.randomUUID();
    UUID transaction_uuid = UUID.randomUUID();
    Client client = new Client(client_uuid, "username1", "password1");
    Account account = new Account(iban, client, "USD");
    Transaction transaction = new Transaction(transaction_uuid, "USD100", "Buy a dog house", account);

    when(transactionRepository.findById(transaction_uuid)).thenReturn(Optional.of(transaction));

    mockMvc.perform(get("/api/transaction/one")
        .param("id", transaction_uuid.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("username1"))
        .andExpect(jsonPath("$.transaction_uuid").value(transaction_uuid.toString()))
        .andExpect(jsonPath("$.date").exists())
        .andExpect(jsonPath("$.amount").value("USD100"))
        .andExpect(jsonPath("$.description").value("Buy a dog house"))
        .andExpect(jsonPath("$.client_uuid").value(client_uuid.toString()))
        .andExpect(jsonPath("$.account_iban").value(iban))
        .andDo(print());
  }

  @Test
  public void clientNotFoundTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    when(transactionRepository.findById(uuid)).thenReturn(Optional.empty());
    mockMvc.perform(get("/api/client/one")
        .param("id", uuid.toString()))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  public void deleteTransactionTest() throws Exception {
    UUID uuid = UUID.randomUUID();

    when(transactionRepository.existsById(uuid)).thenReturn(true);
    doNothing().when(transactionRepository).deleteById(uuid);

    mockMvc.perform(delete("/api/transaction/delete").with(csrf())
        .param("id", uuid.toString()))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

  @Test
  public void updateTransactionTest() throws Exception {
    String iban = Iban.random().toFormattedString();
    UUID client_uuid = UUID.randomUUID();
    UUID transaction_uuid = UUID.randomUUID();
    Client client = new Client(client_uuid, "username1", "password1");
    Account account = new Account(iban, client, "USD");
    Transaction transaction = new Transaction(transaction_uuid, "USD100", "Buy a dog house", account);

    when(transactionRepository.findById(transaction_uuid)).thenReturn(Optional.of(transaction));

    Map<String, Object> input = new HashMap<>();
    input.put("amount", "USD120");
    input.put("description", "Buy a dog door");

    mockMvc.perform(put("/api/transaction/update").with(csrf())
        .param("id", transaction_uuid.toString())
        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("username1"))
        .andExpect(jsonPath("$.transaction_uuid").value(transaction_uuid.toString()))
        .andExpect(jsonPath("$.date").exists())
        .andExpect(jsonPath("$.amount").value("USD120"))
        .andExpect(jsonPath("$.description").value("Buy a dog door"))
        .andExpect(jsonPath("$.client_uuid").value(client_uuid.toString()))
        .andExpect(jsonPath("$.account_iban").value(iban))
        .andDo(print());
  }

}
