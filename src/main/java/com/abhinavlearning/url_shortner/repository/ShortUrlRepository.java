package com.abhinavlearning.url_shortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhinavlearning.url_shortner.entity.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    Optional<ShortUrl> findByShortCode(String shortCode);

}
