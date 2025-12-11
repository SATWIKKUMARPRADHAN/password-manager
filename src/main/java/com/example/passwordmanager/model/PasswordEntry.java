package com.example.passwordmanager.model;

import jakarta.persistence.*;
import java.time.Instant;
import com.example.passwordmanager.model.User;

@Entity
public class PasswordEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String website;
    private String username;
    private String password; // stored hashed
    private String category;

    private Instant createdAt = Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    public PasswordEntry() {
    }

    public PasswordEntry(String website, String username, String password) {
        this.website = website;
        this.username = username;
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
