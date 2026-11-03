package com.abhinavlearning.url_shortner.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abhinavlearning.url_shortner.entity.ShortUrl;
import com.abhinavlearning.url_shortner.enums.ShortCodeMethod;
import com.abhinavlearning.url_shortner.records.response.ShortUrlResponse;
import com.abhinavlearning.url_shortner.repository.ShortUrlRepository;
import com.abhinavlearning.url_shortner.utils.HashGenerator;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private UrlService urlService;

    @Test
    void savesHashCodeWithoutSaltWhenThereIsNoCollision() {
        String originalUrl = "https://example.com";
        when(shortUrlRepository.findByShortCode(HashGenerator.sha256(originalUrl)))
                .thenReturn(Optional.empty());

        ShortUrlResponse response = urlService.generateShortUrl(originalUrl);
        String shortCode = response.shortCode();

        ArgumentCaptor<ShortUrl> captor = ArgumentCaptor.forClass(ShortUrl.class);
        verify(shortUrlRepository).save(captor.capture());
        ShortUrl saved = captor.getValue();

        assertEquals(HashGenerator.sha256(originalUrl), shortCode);
        assertEquals(originalUrl, saved.getOriginalUrl());
        assertEquals(shortCode, saved.getShortCode());
        assertEquals(ShortCodeMethod.HASH, saved.getGenerationMethod());
        assertNull(saved.getHashSalt());
        assertNull(saved.getHashAttempt());
    }

    @Test
    void regeneratesCodeAndStoresSuccessfulAttemptAfterCollision() {
        String originalUrl = "https://example.com/collision";
        when(shortUrlRepository.findByShortCode(anyString()))
                .thenReturn(Optional.of(new ShortUrl()))
                .thenReturn(Optional.empty());

        ShortUrlResponse response = urlService.generateShortUrl(originalUrl);
        String shortCode = response.shortCode();

        ArgumentCaptor<ShortUrl> captor = ArgumentCaptor.forClass(ShortUrl.class);
        verify(shortUrlRepository).save(captor.capture());
        ShortUrl saved = captor.getValue();

        assertEquals(6, shortCode.length());
        assertEquals(1, saved.getHashAttempt());
        assertEquals(16, saved.getHashSalt().length());
        assertEquals(shortCode, saved.getShortCode());
    }
}
