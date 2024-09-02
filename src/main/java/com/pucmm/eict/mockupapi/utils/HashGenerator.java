package com.pucmm.eict.mockupapi.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashGenerator {

    public static String generarHash() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] randomBytes = new byte[16];
            secureRandom.nextBytes(randomBytes);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(randomBytes);

            BigInteger hashNum = new BigInteger(1, hashBytes);
            String hashString = hashNum.toString(36);

            return hashString.substring(0, 7); // devuelve los primeros 7 caracteres del hash como la URL acortada
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
