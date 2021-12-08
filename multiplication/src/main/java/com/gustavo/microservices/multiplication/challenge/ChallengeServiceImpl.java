package com.gustavo.microservices.multiplication.challenge;

import com.gustavo.microservices.multiplication.user.User;
import com.gustavo.microservices.multiplication.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository attemptRepository;

    @Autowired
    public ChallengeServiceImpl(UserRepository userRepository, ChallengeAttemptRepository attemptRepository) {
        this.userRepository = userRepository;
        this.attemptRepository = attemptRepository;
    }

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        int correctAnswer = attemptDTO.getFactorA() * attemptDTO.getFactorB();
        boolean correct = correctAnswer == attemptDTO.getGuess();

        User user = userRepository
                .findByAlias(attemptDTO.getUserAlias())
                .orElseGet(() -> userRepository.save(new User(attemptDTO.getUserAlias())));

        ChallengeAttempt attempt = new ChallengeAttempt(null, user, attemptDTO.getFactorA(),
                attemptDTO.getFactorB(), attemptDTO.getGuess(), correct);
        attemptRepository.save(attempt);
        return attempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.getLatestByUserAlias(userAlias);
    }
}
