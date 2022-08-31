package com.srt.srt.controller;

import com.srt.srt.api.ApiResponse;
import com.srt.srt.dto.EncodeRequestDto;
import com.srt.srt.service.UrlService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"${itj.web.server}", "${itj.web.local}"})
public class UrlController {

    private final UrlService urlService;

    @Value("${itj.web.local}")
    private String local;

    @PostMapping("/api/shorten")
    public ApiResponse shortenUrl(@RequestBody EncodeRequestDto encodeRequestDto){
        return ApiResponse.success(HttpStatus.OK, local + urlService.getShortUrl(encodeRequestDto));
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String str = urlService.redirect(shortUrl);
        RedirectView redirectView = new RedirectView(str);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return redirectView;
    }

    @GetMapping("/api/{originUrl}")
    public long count(@PathVariable String originUrl){
        return urlService.countByOriginUrl(originUrl);
    }
}
