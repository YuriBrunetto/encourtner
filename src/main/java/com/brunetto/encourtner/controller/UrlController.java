package com.brunetto.encourtner.controller;

import com.brunetto.encourtner.dto.UrlRequestDTO;
import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "URL Management", description = "API endpoints for creating and managing URLs")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @Operation(summary = "Create a new shortened URL", description = "Receives a long URL and shortens it, returning the details of the new URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "URL successfully shortened",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Url.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request (e.g., empty or malformed URL)")
    })
    @PostMapping("/shorten")
    public ResponseEntity<Url> createShortUrl(@Valid @RequestBody UrlRequestDTO request) {
        Url createdUrl = urlService.generateShortUrl(request.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUrl);
    }
}
