package ru.job4j.cinema.services;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.UserStore;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@ThreadSafe
@RequiredArgsConstructor
public class UserService {

    private final UserStore store;

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findUser(User user) {
        return store.findUser(user);
    }

    public Optional<User> findUserByName(String username) {
        return store.findUserByName(username);
    }

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
