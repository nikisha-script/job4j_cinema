package ru.job4j.cinema.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Ticket {

    private int id;
    private int row;
    private int userId;
    private int filmId;

    public Ticket(int id, int row, int userId, int filmId) {
        this.id = id;
        this.row = row;
        this.userId = userId;
        this.filmId = filmId;
    }

    public Ticket(int row, int userId, int filmId) {
        this.row = row;
        this.userId = userId;
        this.filmId = filmId;
    }
}
