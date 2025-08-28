package com.brunetto.encourtner.service;

import com.brunetto.encourtner.exception.UrlNotFoundException;
import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.repository.UrlRepository;
import com.brunetto.encourtner.util.UrlUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Url getOriginalUrlAndIncrementViews(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("URL não encontrada para o código: " + shortCode));
        url.setViews(url.getViews() + 1);
        urlRepository.save(url);

        return url;
    }

}
