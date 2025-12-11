package com.example.passwordmanager.repository;

import com.example.passwordmanager.model.PasswordEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PasswordRepository extends JpaRepository<PasswordEntry, Long> {
    List<PasswordEntry> findAllByOwnerUsernameOrderByCreatedAtDesc(String username);

    java.util.Optional<PasswordEntry> findByIdAndOwnerUsername(Long id, String username);
}