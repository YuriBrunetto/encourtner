package com.brunetto.encourtner.controller;

import com.brunetto.encourtner.dto.UrlRequestDTO;
import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Url> createShortUrl(@RequestBody UrlRequestDTO request) {
        Url createdUrl = urlService.generateShortUrl(request.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        Url originalUrl = urlService.getOriginalUrlAndIncrementViews(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl.getLongUrl()))
                .build();
    }
}
