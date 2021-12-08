package com.gustavo.microservices.multiplication.challenge;

public interface ChallengeGeneratorService {

    /**
     * @return a randomly generated challenge between 11 and 99
     */
    Challenge generateChallenge();
}
