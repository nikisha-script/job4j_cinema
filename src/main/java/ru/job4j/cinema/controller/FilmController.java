package ru.job4j.cinema.controller;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.service.FilmService;
import org.springframework.core.io.Resource;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.utils.SessionUser;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
@RequiredArgsConstructor
public class FilmController {

    private final FilmService service;
    private final TicketService ticketService;

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        SessionUser.getSession(model, session);
        model.addAttribute("films", service.findAll());
        return "index";
    }

    @GetMapping("/session/{id}")
    public String getSession(Model model, @PathVariable("id") Integer id, HttpSession session) {
        SessionUser.getSession(model, session);
        Optional<Film> rsl = service.findById(id);
        if (rsl.isEmpty()) {
            return "/404";
        }
        model.addAttribute("film", rsl.get());
        model.addAttribute("rows", ticketService.rowList());
        model.addAttribute("cells", ticketService.cellList());
        return "/session";
    }

    @GetMapping("/getIndex")
    public String getIndex(Model model, HttpSession session) {
        SessionUser.getSession(model, session);
        model.addAttribute("films", service.findAll());
        return "redirect:/index";
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> img(@PathVariable("id") Integer id) {
        Film film = service.findById(id).orElseThrow(() -> {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image is not found");
        });
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(film.getImg().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(film.getImg()));
    }

}
