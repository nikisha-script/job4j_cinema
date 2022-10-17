package ru.job4j.cinema.service;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.store.FilmStore;

import java.util.Collection;
import java.util.Optional;

@Service
@ThreadSafe
@RequiredArgsConstructor
public class FilmService {

    private final FilmStore store;

    public Collection<Film> findAll() {
        return store.findAll();
    }

    public void add(Film film) {
        store.add(film);
    }

    public Optional<Film> findById(int id) {
        return store.findById(id);
    }

}
