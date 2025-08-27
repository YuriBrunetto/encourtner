package com.brunetto.encourtner.service;

import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.repository.UrlRepository;
import com.brunetto.encourtner.util.UrlUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final UrlUtil urlUtil;

    public UrlService(UrlRepository urlRepository, UrlUtil urlUtil) {
        this.urlRepository = urlRepository;
        this.urlUtil = urlUtil;
    }

    public Url generateShortUrl(String longUrl) {
        Optional<Url> existingUrl = urlRepository.findByLongUrl(longUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get();
        }

        String shortCode;
        do {
            shortCode = urlUtil.generateRandomString(6);
        } while (urlRepository.existsByShortCode(shortCode));

        Url newUrl = new Url();
        newUrl.setLongUrl(longUrl);
        newUrl.setShortCode(shortCode);

        return urlRepository.save(newUrl);
    }

    public Url getOriginalUrl(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL não encontrada par o código: " + shortCode));
    }
}
