package com.brunetto.encourtner.repository;

import com.brunetto.encourtner.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByLongUrl(String longUrl);

    Optional<Url> findByShortCode(String shortCode);

    boolean existsByShortCode(String shortCode);
}
