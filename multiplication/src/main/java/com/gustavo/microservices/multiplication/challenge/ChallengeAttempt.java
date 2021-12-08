package com.gustavo.microservices.multiplication.challenge;

import com.gustavo.microservices.multiplication.user.User;
import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;


/**
 * Represents an attempt from a {@link User} to solve a challenge.
 */
@Entity
@Table(name="TB_CHALLENGE_ATTEMPT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    private int factorA;
    private int factorB;
    private int resultAttempt;
    private boolean correct;
}
