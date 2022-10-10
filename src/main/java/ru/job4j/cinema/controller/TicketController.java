package ru.job4j.cinema.controller;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.services.FilmService;
import ru.job4j.cinema.services.TicketService;
import ru.job4j.cinema.services.UserService;
import ru.job4j.cinema.utils.SessionUser;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;
    private final FilmService filmService;

    @GetMapping("/selectTicket")
    public String buyTicket(@RequestParam("id") Integer id,
                            @RequestParam("username") String username,
                            @RequestParam("row") Integer row,
                            @RequestParam("cell") Integer cell,
                            Model model,
                            HttpSession session) {
        SessionUser.getSession(model, session);
        User user = userService.findUserByName(username).get();
        Ticket ticket = new Ticket(row, cell, user.getId(), id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("film", filmService.findById(id).get());
        return "/buyTicket";
    }

    @PostMapping("/buyTicket")
    public String buy(@RequestParam("id") Integer id,
                      @RequestParam("username") String username,
                      @RequestParam("cell") Integer cell,
                      @RequestParam("row") Integer row,
                      Model model,
                      HttpSession session) {
        StringBuilder response = new StringBuilder();
        SessionUser.getSession(model, session);
        User user = userService.findUserByName(username).get();
        Ticket ticket = new Ticket(row, cell, user.getId(), id);
        Optional<Ticket> temp = ticketService.findTicketByRowPosition(ticket.getRow(),
                ticket.getCell(),
                ticket.getFilmId());
        if (temp.isEmpty()) {
            Optional<Ticket> rsl = ticketService.save(ticket);
            if (rsl.isPresent()) {
                response.append("/successfully");
            } else {
                response.append("redirect:/wrongticket?fail=true");
            }
        } else {
            response.append("redirect:/wrongticket?fail=true");
        }
        return response.toString();
    }

    @GetMapping("/wrongticket")
    public String wrong(Model model,
                        @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail);
        return "wrong";
    }

}
