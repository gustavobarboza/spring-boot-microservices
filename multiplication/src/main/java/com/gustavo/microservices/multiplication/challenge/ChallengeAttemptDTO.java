package com.gustavo.microservices.multiplication.challenge;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Value
public class ChallengeAttemptDTO {
    @Min(0)
    int factorA;
    @Min(0)
    int factorB;
    @NotBlank
    String userAlias;
    @Positive(message = "The guess must be a positive number")
    int guess;
}
