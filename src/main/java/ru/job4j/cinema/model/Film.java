package ru.job4j.cinema.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Film {

    private int id;
    private String name;
    private String description;
    private byte[] img;

}
