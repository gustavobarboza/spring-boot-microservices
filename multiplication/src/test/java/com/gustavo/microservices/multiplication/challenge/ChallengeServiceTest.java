package com.gustavo.microservices.multiplication.challenge;

import com.gustavo.microservices.multiplication.user.User;
import com.gustavo.microservices.multiplication.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    private ChallengeService challengeService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ChallengeAttemptRepository attemptRepository;

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(userRepository, attemptRepository);
    }

    @Test
    public void checkCorrectAttempt() {
        //given
        when(attemptRepository.save(any())).then(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(20, 30, "john_doe", 600);

        //when
        ChallengeAttempt challengeAttempt = challengeService.verifyAttempt(attemptDTO);

        //then
        then(challengeAttempt.isCorrect()).isTrue();
        verify(userRepository).save(new User("john_doe"));
        verify(attemptRepository).save(challengeAttempt);
    }

    @Test
    public void checkIncorrectAttempt() {
        //given
        doAnswer(returnsFirstArg()).when(attemptRepository).save(any());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(20, 30, "john_doe", 500);

        //when
        ChallengeAttempt challengeAttempt = challengeService
                .verifyAttempt(attemptDTO);

        //then
        then(challengeAttempt.isCorrect()).isFalse();
        verify(userRepository).save(new User("john_doe"));
        verify(attemptRepository).save(challengeAttempt);
    }

    @Test
    public void checkExistingUser() {
        //given
        User user = new User(1L, "john_doe");
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.of(user));
        doAnswer(returnsFirstArg()).when(attemptRepository).save(any());

        ChallengeAttemptDTO attemptDTO =
                new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
        //when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        //then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(user);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void checkCanRetrieveLastAttemptsFromUser(){
        //given
        ChallengeAttempt attempt = new ChallengeAttempt(1L, new User("john_doe"), 30, 50, 600, true);
        List<ChallengeAttempt> attempts = List.of(attempt);
        given(attemptRepository.getLatestByUserAlias(anyString())).willReturn(attempts);

        //when
        List<ChallengeAttempt> storedAttempts = challengeService.getStatsForUser("john_doe");

        //then
        then(storedAttempts).isEqualTo(attempts);
    }

}