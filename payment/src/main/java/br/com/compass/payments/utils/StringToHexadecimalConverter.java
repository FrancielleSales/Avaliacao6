package br.com.compass.payments.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class StringToHexadecimalConverter {

    public static String convertStringToHex(String str) {

        byte[] getBytesFromString = str.getBytes(StandardCharsets.UTF_8);
        BigInteger bigInteger = new BigInteger(1, getBytesFromString);

        return String.format("%x", bigInteger);
    }
}
