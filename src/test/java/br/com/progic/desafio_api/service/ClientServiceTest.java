package br.com.progic.desafio_api.service;

import br.com.progic.desafio_api.dto.UserDTO;
import br.com.progic.desafio_api.exception.UserNotFoundException;
import br.com.progic.desafio_api.model.User;
import br.com.progic.desafio_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repository;
    private UserService service;

    @BeforeEach
    void setup() {
        repository = mock(UserRepository.class);
        service = new UserService(repository);
    }

    @Test
    void testGetByIdFound() {
        User user = new User(1L, "João Silva", "joao.silva@example.com", LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO dto = service.getById(1L);

        assertThat(dto.name()).isEqualTo("João Silva");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void testCreateUser() {
        User user = new User(null, "Maria Souza", "maria.souza@example.com", LocalDateTime.now());
        User saved = new User(1L, "Maria Souza", "maria.souza@example.com", LocalDateTime.now());

        when(repository.save(any(User.class))).thenReturn(saved);

        UserDTO dto = service.create(new UserDTO(null, "Maria Souza", "maria.souza@example.com", null));

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("Maria Souza");
    }

}