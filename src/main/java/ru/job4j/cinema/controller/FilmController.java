package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.services.FilmService;
import org.springframework.core.io.Resource;
import ru.job4j.cinema.utils.SessionUser;

import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class FilmController {

    private FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        SessionUser.getSession(model, session);
        model.addAttribute("films", service.findAll());
        return "index";
    }

    @GetMapping("/session/{id}")
    public String getSession(Model model, @PathVariable("id") Integer id, HttpSession session) {
        SessionUser.getSession(model, session);
        model.addAttribute("film", service.findById(id));
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
        Film film = service.findById(id);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(film.getImg().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(film.getImg()));
    }

}
