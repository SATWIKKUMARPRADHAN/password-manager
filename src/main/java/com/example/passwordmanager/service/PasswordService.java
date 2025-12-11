package com.example.passwordmanager.service;

import com.example.passwordmanager.model.PasswordEntry;
import com.example.passwordmanager.repository.PasswordRepository;
import com.example.passwordmanager.repository.UserRepository;
import com.example.passwordmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.passwordmanager.service.EncryptionService;

@Service
public class PasswordService {

    @Autowired
    private PasswordRepository repository;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private UserRepository userRepository;

    public List<PasswordEntry> getAll() {
        return repository.findAll();
    }

    public List<PasswordEntry> getAllForUser(String username) {
        return repository.findAllByOwnerUsernameOrderByCreatedAtDesc(username);
    }

    public void save(PasswordEntry entry) {
        saveEntity(entry, true);
    }

    public void saveForUser(PasswordEntry entry, String username) {
        User u = userRepository.findByUsername(username).orElse(null);
        if (u != null) {
            entry.setOwner(u);
            saveEntity(entry, true);
        }
    }

    public void saveEntity(PasswordEntry entry, boolean encryptPassword) {
        if (encryptPassword && entry.getPassword() != null) {
            entry.setPassword(encryptionService.encrypt(entry.getPassword()));
        }
        repository.save(entry);
    }

    public String getDecryptedPassword(Long id) {
        return repository.findById(id)
                .map(e -> encryptionService.decrypt(e.getPassword()))
                .orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public boolean deleteForUser(Long id, String username) {
        return repository.findByIdAndOwnerUsername(id, username).map(e -> {
            repository.delete(e);
            return true;
        }).orElse(false);
    }

    public java.util.Optional<PasswordEntry> findByIdAndOwner(Long id, String username) {
        return repository.findByIdAndOwnerUsername(id, username);
    }
}