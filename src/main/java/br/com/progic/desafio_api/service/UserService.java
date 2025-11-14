package br.com.progic.desafio_api.service;

import br.com.progic.desafio_api.dto.UserDTO;
import br.com.progic.desafio_api.exception.UserNotFoundException;
import br.com.progic.desafio_api.model.User;
import br.com.progic.desafio_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    @Cacheable("users")
    public UserDTO getById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserDTO.fromEntity(user);
    }

    public Page<UserDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(UserDTO::fromEntity);
    }

    public UserDTO create(UserDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        User saved = repository.save(user);
        return UserDTO.fromEntity(saved);
    }

    public UserDTO update(Long id, UserDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setName(dto.name());
        user.setEmail(dto.email());
        User updated = repository.save(user);
        return UserDTO.fromEntity(updated);
    }

    @CacheEvict(value = "users", key = "#id")
    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        repository.delete(user);
    }
}