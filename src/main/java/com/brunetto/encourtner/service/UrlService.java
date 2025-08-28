package com.brunetto.encourtner.service;

import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.repository.UrlRepository;
import com.brunetto.encourtner.util.UrlUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url generateShortUrl(String longUrl) {
        Optional<Url> existingUrl = urlRepository.findByLongUrl(longUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get();
        }

        String shortCode;
        do {
            shortCode = UrlUtil.generateRandomString(6);
        } while (urlRepository.existsByShortCode(shortCode));

        Url newUrl = new Url();
        newUrl.setLongUrl(longUrl);
        newUrl.setShortCode(shortCode);

        return urlRepository.save(newUrl);
    }

    public Url getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL não encontrada para o código: " + shortCode));
    }
}
