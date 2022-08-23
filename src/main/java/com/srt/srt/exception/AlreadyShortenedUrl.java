package com.srt.srt.exception;

public class AlreadyShortenedUrl extends RuntimeException {

    private static String MESSAGE = "이미 축약된 URL 입니다.";

    public AlreadyShortenedUrl(){
        super(MESSAGE);
    }
}
