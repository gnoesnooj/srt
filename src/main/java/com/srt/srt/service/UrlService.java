package com.srt.srt.service;

import com.srt.srt.domain.UrlLine;
import com.srt.srt.dto.EncodeRequestDto;
import com.srt.srt.exception.AlreadyShortenedUrl;
import com.srt.srt.exception.NoSuchUrlLineException;
import com.srt.srt.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public String getShortUrl(EncodeRequestDto encodeRequestDto) {
        String originUrl = encodeRequestDto.getOriginUrl();

        isShortened(originUrl);

        char[] originArr = originUrl.toCharArray();

        String shortUrl = Encoder.encoding(originArr);

        urlRepository.save(buildUrlLine(originUrl, shortUrl));

        return shortUrl;
    }

    public String redirect(String str) {
        UrlLine urlLine = urlRepository.findByShortUrl(str).orElseThrow(NoSuchUrlLineException::new);
        return urlLine.getOriginUrl();
    }

    public long countByOriginUrl(String url) {
        url = checkOriginUrl(url);
        return urlRepository.countByOriginUrl(url);
    }

    private UrlLine buildUrlLine(String originUrl, String shortUrl) {
        originUrl = checkOriginUrl(originUrl);
        UrlLine urlLine = UrlLine.builder()
                .shortUrl(shortUrl)
                .originUrl(originUrl)
                .build();

        return urlLine;
    }

    private String checkOriginUrl(String originUrl) {
        if (originUrl.startsWith("https://")) {
            return originUrl;
        } else {
            return "https://" + originUrl;
        }
    }

    private void isShortened(String originUrl) {
        if (originUrl.startsWith("localhost:8080/")) {
            throw new AlreadyShortenedUrl();
        }
    }
}
