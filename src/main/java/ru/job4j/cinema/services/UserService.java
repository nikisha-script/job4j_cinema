package ru.job4j.cinema.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.store.UsersStore;

import java.util.Optional;

@Service
@ThreadSafe
public class UserService {

    private final UsersStore store;

    public UserService(UsersStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findUser(User user) {
        return store.findUser(user);
    }

    public User findUserByName(String username) {
        return store.findUserByName(username);
    }
}
