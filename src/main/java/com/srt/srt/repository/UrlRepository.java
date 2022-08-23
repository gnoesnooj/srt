package com.srt.srt.repository;

import com.srt.srt.domain.UrlLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlLine, Long> {

    Optional<UrlLine> findByShortUrl(String shortUrl);

    long countByOriginUrl(String originUrl);
}
