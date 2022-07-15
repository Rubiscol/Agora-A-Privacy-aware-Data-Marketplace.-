package com.example.uropproject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Calculator {

    public static BigInteger doSHA256(BigInteger label) throws NoSuchAlgorithmException {
        // TODO Auto-generated method stub
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(label.toString().getBytes(StandardCharsets.UTF_8));
        return new BigInteger(hash).abs();
    }
    public static BigInteger doSHA256(Integer n) throws NoSuchAlgorithmException {
        // TODO Auto-generated method stub
        BigInteger bigInteger = BigInteger.valueOf(n);
        return doSHA256(bigInteger);
    }



}