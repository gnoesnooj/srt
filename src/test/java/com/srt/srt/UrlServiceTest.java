package com.srt.srt;

import com.srt.srt.domain.UrlLine;
import com.srt.srt.dto.EncodeRequestDto;
import com.srt.srt.exception.NoSuchUrlLineException;
import com.srt.srt.repository.UrlRepository;
import com.srt.srt.service.Encoder;
import com.srt.srt.service.UrlService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.util.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    private UrlLine urlLine;

    private EncodeRequestDto encodeRequestDto;
    String shortUrl;

    @BeforeEach
    void setUp(){
        encodeRequestDto = new EncodeRequestDto("www.naver.com");
        shortUrl = urlService.getShortUrl(encodeRequestDto);

        urlLine = UrlLine.builder()
                .originUrl(encodeRequestDto.getOriginUrl())
                .shortUrl(shortUrl)
                .build();

        urlRepository.save(urlLine);
    }

    @Test
    void COUNT_TEST_success(){
        urlRepository.save(urlLine);
        Assertions.assertThat(urlService.countByOriginUrl("https://www.naver.com")).isNotNull(); // should be 1
    }

    @Test
    void COUNT_TEST_FAIL(){
        Assertions.assertThat(urlService.countByOriginUrl("www.naver.com")).isNotEqualTo(1); // should be 0
    }

    @Test
    void REDIRECT_TEST_SUCCESS(){
        given(urlService.redirect(any())).willReturn(urlLine.getOriginUrl());
        Assertions.assertThat(urlService.redirect(shortUrl)).isEqualTo(urlLine.getOriginUrl());
    }

    @Test
    void REDIRECT_TEST_FAIL(){
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchUrlLineException.class, () -> {
            urlService.redirect("www.github.io");
        });
    }
}
