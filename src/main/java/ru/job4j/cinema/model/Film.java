package ru.job4j.cinema.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    private int id;
    private String name;
    private String description;
    private byte[] img;

}
