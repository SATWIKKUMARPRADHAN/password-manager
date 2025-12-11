package com.example.passwordmanager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EncryptionService {

    private final SecretKeySpec keySpec;
    private final SecureRandom secureRandom = new SecureRandom();

    public EncryptionService(@Value("${encryption.key:change-this-please-32bkey1234567890abc}") String key) {
        // Ensure key length is 16/24/32 bytes for AES
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 16) {
            byte[] copy = new byte[16];
            System.arraycopy(keyBytes, 0, copy, 0, Math.min(keyBytes.length, 16));
            keyBytes = copy;
        } else if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            // Trim or extend to 32 bytes
            byte[] copy = new byte[32];
            System.arraycopy(keyBytes, 0, copy, 0, Math.min(keyBytes.length, 32));
            keyBytes = copy;
        }
        this.keySpec = new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String plain) {
        try {
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
            byte[] out = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, out, 0, iv.length);
            System.arraycopy(encrypted, 0, out, iv.length, encrypted.length);
            return Base64.getEncoder().encodeToString(out);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public String decrypt(String cipherText) {
        try {
            byte[] all = Base64.getDecoder().decode(cipherText);
            byte[] iv = new byte[16];
            System.arraycopy(all, 0, iv, 0, iv.length);
            byte[] enc = new byte[all.length - iv.length];
            System.arraycopy(all, iv.length, enc, 0, enc.length);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decrypted = cipher.doFinal(enc);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
