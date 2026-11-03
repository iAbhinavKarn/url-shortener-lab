package com.abhinavlearning.url_shortner.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.abhinavlearning.url_shortner.records.request.UrlRequest;
import com.abhinavlearning.url_shortner.records.response.ShortUrlResponse;
import com.abhinavlearning.url_shortner.service.UrlService;

class UrlControllerTest {

    private final UrlService urlService = Mockito.mock(UrlService.class);
    private final UrlController urlController = new UrlController(urlService);

    @Test
    void returnsHealthMessage() {
        assertEquals("Hello World", urlController.getUrl());
    }

    @Test
    void passesUrlValueToService() {
        String originalUrl = "https://example.com";
        when(urlService.generateShortUrl(originalUrl)).thenReturn(new ShortUrlResponse("abc123"));

        assertEquals("abc123", urlController.getShortUrl(new UrlRequest(originalUrl)).shortCode());
    }
}
