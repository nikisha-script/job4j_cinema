package ru.job4j.cinema.utils;

import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

public class SessionUser {

    public static void getSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setUsername("Account");
        }
        model.addAttribute("user", user);
    }

}
