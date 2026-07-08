package com.mathgame.controller;

import com.mathgame.model.Siswa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/register")
    @Transactional // Mengurus pembukaan dan komit transaksi secara otomatis oleh Spring
    public Map<String, Object> register(@RequestBody Siswa siswa) {
        try {
            List<?> existing = entityManager.createQuery(
                "SELECT s FROM Siswa s WHERE s.username = :u")
                .setParameter("u", siswa.getUsername())
                .getResultList();
            if (!existing.isEmpty()) {
                return Map.of("success", false, "message", "Username sudah dipakai");
            }
            
            // Proses simpan langsung tanpa memanggil getTransaction() secara manual
            entityManager.persist(siswa);
            
            return Map.of("success", true, "message", "Register berhasil");
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Siswa siswa) {
        try {
            List<?> result = entityManager.createQuery(
                "SELECT s FROM Siswa s WHERE s.username = :u AND s.password = :p")
                .setParameter("u", siswa.getUsername())
                .setParameter("p", siswa.getPassword())
                .getResultList();
            if (!result.isEmpty()) {
                return Map.of("success", true, "message", "Login berhasil", "username", siswa.getUsername());
            }
            return Map.of("success", false, "message", "Username atau password salah");
        } catch (Exception e) {
            return Map.of("success", false, "message", e.getMessage());
        }
    }
}