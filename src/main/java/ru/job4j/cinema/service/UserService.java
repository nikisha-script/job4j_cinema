package ru.job4j.cinema.service;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.filter.Md5PasswordEncrypter;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.UserStore;

import java.util.Optional;

@Service
@ThreadSafe
@RequiredArgsConstructor
public class UserService {

    private final UserStore store;
    private final Md5PasswordEncrypter encrypter;

    public Optional<User> add(User user) {
        user.setPassword(encrypter.passwordEncryption(user.getPassword()));
        return store.add(user);
    }

    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
        password = encrypter.passwordEncryption(password);
        return store.findUserByUsernameAndPassword(username, password);
    }

    public Optional<User> findUserByName(String username) {
        return store.findUserByName(username);
    }

}
