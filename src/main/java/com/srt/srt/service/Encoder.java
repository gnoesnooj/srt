package com.srt.srt.service;

import java.util.Random;

public class Encoder {
    private static final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String encoding(char[] originArr) {
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
}
