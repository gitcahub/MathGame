package com.mathgame.controller;

import com.mathgame.model.Leaderboard;
import com.mathgame.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ScoreController {

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @PostMapping("/simpan-skor")
    public ResponseEntity<?> simpanSkor(@RequestBody Leaderboard leaderboard) {
        try {
            Leaderboard hasilSimpan = leaderboardRepository.save(leaderboard);
            return ResponseEntity.ok(hasilSimpan);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Gagal menyimpan skor: " + e.getMessage());
        }
    }
}