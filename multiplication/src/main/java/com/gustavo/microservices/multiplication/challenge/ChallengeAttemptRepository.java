package com.gustavo.microservices.multiplication.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeAttemptRepository extends JpaRepository<ChallengeAttempt, Long> {
    @Query("Select c from ChallengeAttempt c where c.user.alias = :alias order by c.id desc")
    List<ChallengeAttempt> getLatestByUserAlias(String alias);

    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String userAlias);
}
