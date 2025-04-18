package in.finbox.feature_flag.controller;

import in.finbox.feature_flag.service.ClientService;
import in.finbox.feature_flag.model.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    void createClient_returns201AndClientData_whenValidRequest() throws Exception {
        Client dummyClient = Client.builder()
                .id(1L)
                .name("Test Client")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(clientService.createClient("Test Client"))
                .thenReturn(dummyClient);

        mockMvc.perform(post("/clients?name=Test Client"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Client"));
    }

    @Test
    void createClient_returns400_whenNameMissing() throws Exception {
        mockMvc.perform(post("/clients"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return client when valid ID is passed")
    void getClient_returnsClient_whenValidId() throws Exception {
        // Arrange
        Client mockClient = Client.builder()
                .id(1L)
                .name("Finbox")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(clientService.getClient(eq(1L))).thenReturn(mockClient);

        // Act & Assert
        mockMvc.perform(get("/clients")
                        .param("id", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Finbox"));
    }

    @Test
    @DisplayName("Should return 404 when client is not found")
    void getClient_returnsNotFound_whenClientNotFound() throws Exception {
        // Arrange
        when(clientService.getClient(eq(99L)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        // Act & Assert
        mockMvc.perform(get("/clients")
                        .param("id", "99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Client not found"));
    }
}
