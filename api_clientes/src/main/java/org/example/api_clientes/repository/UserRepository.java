package org.example.api_clientes.repository;

import org.example.api_clientes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameAndPassword(String username, byte[] password);
    Optional<User> findByUsername(String username);
}
