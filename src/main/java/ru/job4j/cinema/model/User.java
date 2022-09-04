package ru.job4j.cinema.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    private int id;
    private String username;
    private String email;
    private String phone;
    private String password;

}
