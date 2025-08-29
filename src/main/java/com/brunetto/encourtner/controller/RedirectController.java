package com.brunetto.encourtner.controller;

import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Tag(name = "URL Redirection", description = "Public endpoint to redirect URLs")
public class RedirectController {
    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @Operation(summary = "Redirect to original URL", description = "Finds the original URL from the short code and performs a 302 redirect.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Successful redirection"),
            @ApiResponse(responseCode = "404", description = "Short code not found"),
            @ApiResponse(responseCode = "410", description = "The link existed, but has expired")
    })
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
        Url originalUrl = urlService.getOriginalUrlAndIncrementViews(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl.getLongUrl()))
                .build();
    }
}
