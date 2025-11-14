package br.com.progic.desafio_api.controler;

import br.com.progic.desafio_api.dto.UserDTO;
import br.com.progic.desafio_api.model.User;
import br.com.progic.desafio_api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";

    private final String USER_USERNAME = "user";
    private final String USER_PASSWORD = "user123";

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void testCreateUser() throws Exception {
        UserDTO dto = new UserDTO(null, "João Silva", "joao.silva@example.com", null);

        mockMvc.perform(post("/api/users")
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@example.com"));
    }

    @Test
    void testGetUserById() throws Exception {
        User user = repository.save(new User(null, "Maria Souza", "maria.souza@example.com", LocalDateTime.now()));

        mockMvc.perform(get("/api/users/" + user.getId())
                        .with(httpBasic(USER_USERNAME, USER_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria Souza"))
                .andExpect(jsonPath("$.email").value("maria.souza@example.com"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        repository.save(new User(null, "Carlos Oliveira", "carlos.oliveira@example.com", LocalDateTime.now()));
        repository.save(new User(null, "Ana Pereira", "ana.pereira@example.com", LocalDateTime.now()));

        mockMvc.perform(get("/api/users")
                        .with(httpBasic(USER_USERNAME, USER_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = repository.save(new User(null, "Pedro Lima", "pedro.lima@example.com", LocalDateTime.now()));
        UserDTO updateDto = new UserDTO(null, "Pedro Lima Atualizado", "pedro.lima.atualizado@example.com", null);

        mockMvc.perform(put("/api/users/" + user.getId())
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Pedro Lima Atualizado"))
                .andExpect(jsonPath("$.email").value("pedro.lima.atualizado@example.com"));
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = repository.save(new User(null, "Fernanda Costa", "fernanda.costa@example.com", LocalDateTime.now()));

        mockMvc.perform(delete("/api/users/" + user.getId())
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/" + user.getId())
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testValidationErrors() throws Exception {
        UserDTO invalidDto = new UserDTO(null, "", "email-invalido", null);

        mockMvc.perform(post("/api/users")
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

}