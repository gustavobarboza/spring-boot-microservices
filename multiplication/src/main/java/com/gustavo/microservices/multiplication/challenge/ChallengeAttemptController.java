package com.gustavo.microservices.multiplication.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/attempts")
public class ChallengeAttemptController {
    private final ChallengeService service;

    @PostMapping
    public ResponseEntity<ChallengeAttempt> createAttempt(@RequestBody @Valid ChallengeAttemptDTO dto) {
        return ResponseEntity.ok(service.verifyAttempt(dto));
    }

    @GetMapping
    public ResponseEntity<List<ChallengeAttempt>> getAttemptsByUser(@RequestParam("alias") String alias) {
        return ResponseEntity.ok(service.getStatsForUser(alias));
    }
}
