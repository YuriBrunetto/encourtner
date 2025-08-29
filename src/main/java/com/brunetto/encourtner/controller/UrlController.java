package com.brunetto.encourtner.controller;

import com.brunetto.encourtner.dto.UrlRequestDTO;
import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Url> createShortUrl(@Valid @RequestBody UrlRequestDTO request) {
        Url createdUrl = urlService.generateShortUrl(request.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUrl);
    }
}
