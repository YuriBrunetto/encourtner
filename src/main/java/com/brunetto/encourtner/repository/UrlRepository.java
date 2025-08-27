package com.brunetto.encourtner.repository;

import com.brunetto.encourtner.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByShortCode(String shortCode);
}
