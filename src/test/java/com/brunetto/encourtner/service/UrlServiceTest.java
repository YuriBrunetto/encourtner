package com.brunetto.encourtner.service;

import com.brunetto.encourtner.exception.UrlExpiredException;
import com.brunetto.encourtner.exception.UrlNotFoundException;
import com.brunetto.encourtner.model.Url;
import com.brunetto.encourtner.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {
    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @Test
    void shouldGenerateNewShortUrl_whenLongUrlDoesntExist() {
        // Arrange
        String longUrl = "https://google.com/";

        Mockito.when(urlRepository.findByLongUrl(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(urlRepository.existsByShortCode(Mockito.anyString())).thenReturn(false);
        Mockito.when(urlRepository.save(Mockito.any(Url.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        // Act
        Url result = urlService.generateShortUrl(longUrl);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLongUrl()).isEqualTo(longUrl);
        assertThat(result.getShortCode()).isNotNull();
        assertThat(result.getShortCode().length()).isEqualTo(6);

        Mockito.verify(urlRepository, Mockito.times(1)).save(Mockito.any(Url.class));
        Mockito.verify(urlRepository, Mockito.times(1)).findByLongUrl(longUrl);
    }

    @Test
    void shouldReturnExistingUrl_whenLongUrlAlreadyShorten() {
        // Arrange
        String longUrl = "https://www.ja-existe.com/";

        Url existingUrl = new Url();
        existingUrl.setId("mongo-id");
        existingUrl.setLongUrl(longUrl);
        existingUrl.setShortCode("abc123");

        Mockito.when(urlRepository.findByLongUrl(longUrl)).thenReturn(Optional.of(existingUrl));

        // Act
        Url result = urlService.generateShortUrl(longUrl);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getShortCode()).isEqualTo("abc123");
        assertThat(result.getLongUrl()).isEqualTo(longUrl);

        Mockito.verify(urlRepository, Mockito.never()).save(Mockito.any(Url.class));
    }

    @Test
    void shouldReturnUrlAndIncrementViews_whenShortCodeExists() {
        // Arrange
        String shortCode = "abc123";

        Url existingUrl = new Url();
        existingUrl.setLongUrl("https://um-site-qualquer.com/");
        existingUrl.setShortCode(shortCode);
        existingUrl.setViews(10);
        existingUrl.setExpirationDate(LocalDateTime.now().plusDays(1));

        Mockito.when(urlRepository.findByShortCode(shortCode)).thenReturn(Optional.of(existingUrl));

        // Act
        Url result = urlService.getOriginalUrlAndIncrementViews(shortCode);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getViews()).isEqualTo(11);

        Mockito.verify(urlRepository, Mockito.times(1)).save(existingUrl);
    }

    @Test
    void shouldThrowException_whenShortCodeDoesntExist() {
        // Arrange
        String invalidShortCode = "123abc";

        Mockito.when(urlRepository.findByShortCode(invalidShortCode)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UrlNotFoundException.class, () -> {
            urlService.getOriginalUrlAndIncrementViews(invalidShortCode);
        });

        Mockito.verify(urlRepository, Mockito.never()).save(Mockito.any(Url.class));
    }

    @Test
    void shouldThrowException_whenUrlIsExpired() {
        // Arrange
        String shortCode = "abc123";

        Url existingUrl = new Url();
        existingUrl.setLongUrl("https://um-site-qualquer.com/");
        existingUrl.setShortCode(shortCode);
        existingUrl.setExpirationDate(LocalDateTime.now().minusDays(2));

        Mockito.when(urlRepository.findByShortCode(shortCode)).thenReturn(Optional.of(existingUrl));

        // Act & Assert
        assertThrows(UrlExpiredException.class, () -> {
            urlService.getOriginalUrlAndIncrementViews(shortCode);
        });

        Mockito.verify(urlRepository, Mockito.never()).save(Mockito.any(Url.class));
    }
}
