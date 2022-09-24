package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.services.TicketService;
import ru.job4j.cinema.services.UserService;
import ru.job4j.cinema.utils.SessionUser;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Optional;

@Controller
@ThreadSafe
public class TicketController {

    private TicketService ticketService;
    private UserService userService;

    public TicketController(TicketService service, UserService userService) {
        this.ticketService = service;
        this.userService = userService;
    }


    @GetMapping("/buy")
    public String buyTicket(@RequestParam("id") Integer id,
                            @RequestParam("username") String username,
                            @RequestParam("place") Integer place,
                            Model model,
                            HttpSession session) {
        SessionUser.getSession(model, session);
        User user = userService.findUserByName(username);
        Optional<Ticket> temp = ticketService.findTicketByRowPosition(place, id);
        if (user != null && temp.get().getId() == 0) {
            Ticket ticket = new Ticket(place, user.getId(), id);
            ticketService.save(ticket);
        } else {
            return "redirect:/wrongticket?fail=true";
        }
        return "redirect:/index";
    }

    @GetMapping("/wrongticket")
    public String wrong(Model model,
                        @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail);
        return "wrong";
    }
}
