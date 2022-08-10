package ru.job4j.cinema.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.store.FilmsStore;

import java.util.Collection;

@Service
@ThreadSafe
public class FilmService {

    private FilmsStore store;

    public FilmService(FilmsStore store) {
        this.store = store;
    }

    public Collection<Film> findAll() {
        return store.findAll();
    }

    public void add(Film film) {
        store.add(film);
    }

    public Film findById(int id) {
        return store.findById(id);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public void update(Film film) {
        store.update(film);
    }
}
