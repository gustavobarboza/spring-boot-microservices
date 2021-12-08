package com.gustavo.microservices.multiplication.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ChallengeGeneratorServiceTest {

    private ChallengeGeneratorService challengeGeneratorService;

    @Spy
    private Random random;

    @BeforeEach
    public void setup() {
        challengeGeneratorService = new ChallengeGeneratorServiceImpl(random);

    }

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits() {
        //given
        given(random.nextInt(89)).willReturn(20, 30);
        //when
        Challenge challenge = challengeGeneratorService.generateChallenge();
        //then
        then(challenge).isEqualTo(new Challenge(31, 41));
    }
}