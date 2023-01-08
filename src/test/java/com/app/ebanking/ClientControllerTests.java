package com.app.ebanking;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.app.ebanking.controller.ClientController;
import com.app.ebanking.model.Client;
import com.app.ebanking.repository.ClientRepository;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.Optional;
import java.util.UUID;

@ContextConfiguration
@WebMvcTest(controllers = ClientController.class)
@WithMockUser
class ClientControllerTests {

  @MockBean
  private ClientRepository clientRepository;

  @InjectMocks
  ClientController clientController;

  @Autowired
  private MockMvc mockMvc;

  private static UUID uuid;

  @Test
  void contextLoads() {
  }

  @Test
  public void getClientTest() throws Exception {
    uuid = UUID.randomUUID();
    Client client = new Client(uuid, "username1", "password1");

    when(clientRepository.findById(uuid)).thenReturn(Optional.of(client));

    mockMvc.perform(get("/api/client/one")
        .param("id", uuid.toString()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("username1"))
        .andExpect(jsonPath("$.uuid").value(uuid.toString()))
        .andExpect(jsonPath("$.accounts").exists())
        .andDo(print());
  }

  @Test
  public void clientNotFoundTest() throws Exception {
    UUID uuid = UUID.randomUUID();
    when(clientRepository.findById(uuid)).thenReturn(Optional.empty());
    mockMvc.perform(get("/api/client/one")
        .param("id", uuid.toString()))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  public void deleteClientTest() throws Exception {
    uuid = UUID.randomUUID();
    when(clientRepository.existsById(uuid)).thenReturn(true);

    doNothing().when(clientRepository).deleteById(uuid);

    mockMvc.perform(delete("/api/client/delete").with(csrf())
        .param("id", uuid.toString()))
        .andExpect(status().isNoContent())
        .andDo(print());
  }

}
