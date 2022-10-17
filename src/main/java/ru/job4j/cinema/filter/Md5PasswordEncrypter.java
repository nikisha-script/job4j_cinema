package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Md5PasswordEncrypter {

    public String passwordEncryption(String pass) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        StringBuilder m5dHex = new StringBuilder(bigInteger.toString(16));

        while (m5dHex.length() < 32) {
            m5dHex.insert(0, "0");
        }
        return m5dHex.toString();
    }

}
