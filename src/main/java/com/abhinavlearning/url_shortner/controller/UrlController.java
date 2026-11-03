package com.abhinavlearning.url_shortner.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhinavlearning.url_shortner.records.request.UrlRequest;
import com.abhinavlearning.url_shortner.records.response.ShortUrlResponse;
import com.abhinavlearning.url_shortner.service.UrlService;

@RestController
@RequestMapping("/api/v1/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public String getUrl() {
        return "Hello World";
    }

    @PostMapping("/get-short-url")
    public ShortUrlResponse getShortUrl(@Valid @RequestBody UrlRequest request) {
        return urlService.generateShortUrl(request.url());
    }
    
}
