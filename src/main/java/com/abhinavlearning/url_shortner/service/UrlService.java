package com.abhinavlearning.url_shortner.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.abhinavlearning.url_shortner.entity.ShortUrl;
import com.abhinavlearning.url_shortner.enums.ShortCodeMethod;
import com.abhinavlearning.url_shortner.records.response.ShortUrlResponse;
import com.abhinavlearning.url_shortner.repository.ShortUrlRepository;
import com.abhinavlearning.url_shortner.utils.HashGenerator;
import com.abhinavlearning.url_shortner.utils.RandomStringGenerator;

@Service 
public class UrlService {

    private final ShortUrlRepository shortUrlRepository;

    public UrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public ShortUrlResponse generateShortUrl(String originalUrl) {
        String shortUrl = generateHashString(originalUrl);

        Optional<ShortUrl> existingShortUrl = shortUrlRepository.findByShortCode(shortUrl);

        ShortUrl newShortUrl = new ShortUrl();

        if(existingShortUrl.isPresent()){
            int attempt = 0;
            String nonce = generateNonce();

            while(existingShortUrl.isPresent()){
                attempt++;
                shortUrl = generateHashString(originalUrl, attempt, nonce);
                existingShortUrl = shortUrlRepository.findByShortCode(shortUrl);
            }

            newShortUrl.setHashSalt(nonce);
            newShortUrl.setHashAttempt(attempt);
        }

        newShortUrl.setOriginalUrl(originalUrl);
        newShortUrl.setShortCode(shortUrl);
        newShortUrl.setGenerationMethod(ShortCodeMethod.HASH);
        shortUrlRepository.save(newShortUrl);
        return new ShortUrlResponse(shortUrl);
    }

    private String generateHashString(String originalUrl, int attempt, String nonce) {
        String hashInput = originalUrl + nonce + attempt;
        return HashGenerator.sha256(hashInput);
    }

    private String generateHashString(String originalUrl){
        return HashGenerator.sha256(originalUrl);
    }

    private String generateNonce(){
        return RandomStringGenerator.generateSecureRandomString(16);
    }

}
