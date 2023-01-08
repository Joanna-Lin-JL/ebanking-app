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
import com.app.ebanking.model.Account;
import com.app.ebanking.model.Client;
import com.app.ebanking.repository.AccountRepository;
import com.app.ebanking.repository.ClientRepository;
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
@WebMvcTest(controllers = AccountController.class)
@WithMockUser
class AccountControllerTests {

  @MockBean
  private ClientRepository clientRepository;

  @InjectMocks
  ClientController clientController;

  @MockBean
  private AccountRepository accountRepository;

  @InjectMocks
  AccountController accountController;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void contextLoads() {
  }

  @Test
  public void createAccountTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    Client client = new Client(uuid, "username2", "password2");
    when(clientRepository.findById(uuid)).thenReturn(Optional.of(client));

    Iban iban = Iban.random();
    Account account = new Account(iban.toFormattedString(), client, "USD");
    when(accountRepository.save(any(Account.class))).thenReturn(account);

    Map<String, Object> input = new HashMap<>();
    input.put("currency", "USD");

    mockMvc
        .perform(
            post("/api/account/create").with(csrf())
                .param("client_uuid", uuid.toString())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(input)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.iban").value(iban.toFormattedString()))
        .andExpect(jsonPath("$.currency").value("USD"))
        .andExpect(jsonPath("$.client_uuid").value(uuid.toString()))
        .andDo(print());
  }

  @Test
  public void getAccountTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    Client client = new Client(uuid, "username1", "password1");
    String iban = Iban.random().toFormattedString();

    Account account = new Account(iban, client, "USD");

    when(accountRepository.findById(iban)).thenReturn(Optional.of(account));

    mockMvc.perform(get("/api/account/one")
        .param("iban", iban))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("username1"))
        .andExpect(jsonPath("$.client_uuid").value(uuid.toString()))
        .andExpect(jsonPath("$.iban").value(iban))
        .andExpect(jsonPath("$.currency").value("USD"))
        .andDo(print());
  }

  @Test
  public void accountNotFoundTest() throws Exception {
    String iban = Iban.random().toFormattedString();
    when(accountRepository.findById(iban)).thenReturn(Optional.empty());
    mockMvc.perform(get("/api/account/one")
        .param("iban", iban))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  public void deleteAccountTest() throws Exception {
    String iban = Iban.random().toFormattedString();
    when(accountRepository.existsById(iban)).thenReturn(true);

    doNothing().when(accountRepository).deleteById(iban);

    mockMvc.perform(delete("/api/account/delete").with(csrf())
        .param("iban", iban))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

}
