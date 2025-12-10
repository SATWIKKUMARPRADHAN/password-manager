package com.example.passwordmanager.repository;

import com.example.passwordmanager.model.PasswordEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<PasswordEntry, Long> {
}