package com.srt.srt.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiResponse {

    private HttpStatus httpStatus;
    private String message;

    public static ApiResponse success(HttpStatus httpStatus, String response) {
        return new ApiResponse( httpStatus, response);
    }
}