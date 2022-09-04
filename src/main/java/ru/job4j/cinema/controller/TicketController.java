package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.services.TicketService;
import ru.job4j.cinema.services.UserService;
import ru.job4j.cinema.utils.SessionUser;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class TicketController {

    private TicketService ticketService;
    private UserService userService;

    public TicketController(TicketService service, UserService userService) {
        this.ticketService = service;
        this.userService = userService;
    }

    @GetMapping("/session")
    public String getSession(Model model, HttpSession session) {
        model.addAttribute("sessions", ticketService.findAll());
        return "session";
    }

    @GetMapping("/buy/{id}/{username}/{place}")
    public String buyTicket(@PathVariable("id") Integer id,
                            @PathVariable("username") String username,
                            @PathVariable("place") Integer place,
                            Model model,
                            HttpSession session) {
        SessionUser.getSession(model, session);
        User user = userService.findUserByName(username);
        if (user != null) {
            Ticket ticket = new Ticket(place, user.getId(), id);
            ticketService.save(ticket);
        }
        return "redirect:/index";

    }
}
