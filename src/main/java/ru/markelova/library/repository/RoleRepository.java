package ru.markelova.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.markelova.library.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
