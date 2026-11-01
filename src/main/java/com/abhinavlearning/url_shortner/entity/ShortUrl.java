package com.abhinavlearning.url_shortner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

import com.abhinavlearning.url_shortner.enums.ShortCodeMethod;

@Entity
@Table(
    name = "short_urls",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "short_urls_short_code_key",
            columnNames = "short_code"
        )
    },
    indexes = {
        @Index(
            name = "idx_short_urls_created_at",
            columnList = "created_at"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "short_code",
        length = 64,
        nullable = false,
        unique = true
    )
    private String shortCode;

    @Column(
        name = "original_url",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String originalUrl;

    @Enumerated(EnumType.STRING)
    @Column(
        name = "generation_method",
        length = 20,
        nullable = false
    )
    private ShortCodeMethod generationMethod;

    @Column(name = "hash_salt", length = 100)
    private String hashSalt;

    @Column(name = "hash_attempt")
    private Integer hashAttempt;

    @Column(
        name = "created_at",
        nullable = false,
        insertable = false,
        updatable = false
    )
    private Instant createdAt;

    @Column(name = "expires_at")
    private Instant expiresAt;
}
