package com.mathgame.controller;

import com.mathgame.model.Leaderboard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext; // Disarankan untuk EntityManager
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional; // Tambah import ini

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ScoreController {

    // Menggunakan @PersistenceContext lebih disarankan untuk EntityManager bawaan JPA
    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/score/save")
    @Transactional // <--- Spring akan otomatis mengurus begin dan commit transaksi di sini
    public Map<String, Object> saveScore(@RequestBody Leaderboard leaderboard) {
        Map<String, Object> response = new HashMap<>();
        try {
            // HAPUS baris entityManager.getTransaction().begin();
            
            entityManager.persist(leaderboard);
            
            response.put("status", "success");
            response.put("message", "Score saved successfully");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }
}