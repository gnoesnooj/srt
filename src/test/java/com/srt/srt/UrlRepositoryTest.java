package com.srt.srt;

import com.srt.srt.domain.UrlLine;
import com.srt.srt.repository.UrlRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    private UrlLine urlLine;

    @BeforeEach
    void setUp(){
        urlLine = UrlLine.builder()
                .shortUrl("shortUrl")
                .originUrl("originUrl")
                .build();

        urlRepository.save(urlLine);
    }

    @Test
    void 조회_성공(){
        Assertions.assertThat(urlRepository.findById(1L)).isEqualTo(urlLine);
    }
}
