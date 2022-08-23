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

        if(isShortened(originUrl)) {
            throw new AlreadyShortenedUrl();
        }

        char[] originArr = originUrl.toCharArray();

        String shortUrl = encoding(originArr);

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

    private String encoding(char[] originArr) {
        int[] originASCIIArr = new int[originArr.length];
        char c;
        String encode = "";

        for (int i = 0; i < originArr.length; i++) {
            originASCIIArr[i] = (int) originArr[i] % 62; // 62로 나눈 나머지를 넣어준다.
        }

        for (int i = 0; i < 7; i++) {
            Random random = new Random();
            int x = random.nextInt(originASCIIArr.length);
            c = BASE62[originASCIIArr[x]];
            encode += c;
        }
        return encode;
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

    private boolean isShortened(String originUrl) {
        if (originUrl.startsWith("localhost:8080/")) {
            return false;
        } else {
            return true;
        }
    }
}
