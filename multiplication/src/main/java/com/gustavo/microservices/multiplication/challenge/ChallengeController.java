package com.gustavo.microservices.multiplication.challenge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    private final ChallengeGeneratorService generatorService;

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/random")
    public ResponseEntity<Challenge> generateChallenge(){
        Challenge challenge = generatorService.generateChallenge();
        log.info("Generating a new challenge: {}", challenge);
        return ResponseEntity.ok(challenge);
    }
}
