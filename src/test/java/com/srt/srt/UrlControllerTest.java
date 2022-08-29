package com.srt.srt;

import com.srt.srt.domain.UrlLine;
import com.srt.srt.dto.EncodeRequestDto;
import com.srt.srt.service.UrlService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.RedirectView;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(UrlControllerTest.class)
public class UrlControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UrlService urlService;

    private UrlLine urlLine;

    private EncodeRequestDto encodeRequestDto;

    private String shortUrl;

    @BeforeEach
    void setUp(){
        encodeRequestDto = new EncodeRequestDto("www.naver.com");
        shortUrl = urlService.getShortUrl(encodeRequestDto);

        urlLine = UrlLine.builder()
                .originUrl(encodeRequestDto.getOriginUrl())
                .shortUrl(shortUrl)
                .build();
    }

    @Test
    void REDIRECT_TEST_SUCCESS() throws Exception {
        given(urlService.redirect(any())).willReturn("naver");
        mvc.perform(get("/something"))
                .andExpect(status().isOk());
    }

    @Test
    void SHORTEN_TEST_SUCCESS() throws Exception {
        given(urlService.getShortUrl(any())).willReturn("shorten_url");
        mvc.perform(post("/api/shorten"))
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("localhost")));
    }

    @Test
    void ORIGIN_URL_GET_TEST() throws Exception {
        given(urlService.countByOriginUrl(any())).willReturn(10L);
        mvc.perform(get("/api/originUrl"))
                .andExpect(status().isOk());
    }
}
