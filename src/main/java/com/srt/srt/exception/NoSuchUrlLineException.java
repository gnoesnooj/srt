package com.srt.srt.exception;

public class NoSuchUrlLineException extends RuntimeException{

    private static String MESSAGE = "존재하지 않는 URL 입니다.";

    public NoSuchUrlLineException(){
        super(MESSAGE);
    }
}
