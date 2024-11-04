package ru.markelova.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.markelova.library.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
