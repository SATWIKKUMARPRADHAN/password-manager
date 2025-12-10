package com.example.passwordmanager.service;

import com.example.passwordmanager.model.PasswordEntry;
import com.example.passwordmanager.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PasswordService {

    @Autowired
    private PasswordRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<PasswordEntry> getAll() {
        return repository.findAll();
    }

    public void save(PasswordEntry entry) {
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        repository.save(entry);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}