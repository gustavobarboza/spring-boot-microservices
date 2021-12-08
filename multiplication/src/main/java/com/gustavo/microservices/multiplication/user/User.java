package com.gustavo.microservices.multiplication.user;

import lombok.*;

import javax.persistence.*;

/**
 * Stores information to identify a user.
 */

@Entity
@Table(name="TB_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String alias;

    public User(final String alias) {
        this(null, alias);
    }
}
