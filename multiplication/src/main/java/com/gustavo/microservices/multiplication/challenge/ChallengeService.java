package com.gustavo.microservices.multiplication.challenge;

import java.util.List;

/**
 * Verify if an attempt coming from the presentation layer is correct or not
 */
public interface ChallengeService {
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);

    List<ChallengeAttempt> getStatsForUser(String userAlias);
}
