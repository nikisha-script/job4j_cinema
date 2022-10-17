package ru.job4j.cinema.controller;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.filter.Md5PasswordEncrypter;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final Md5PasswordEncrypter encrypter;

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        user.setPassword(encrypter.passwordEncryption(user.getPassword()));
        Optional<User> userDb = userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/reg")
    public String getReg() {
        return "reg";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, HttpServletRequest req) {
        user.setPassword(encrypter.passwordEncryption(user.getPassword()));
        Optional<User> regUser = userService.add(user);
        HttpSession session = req.getSession();
        session.setAttribute("user", regUser.get());
        return "redirect:/index";
    }

}
