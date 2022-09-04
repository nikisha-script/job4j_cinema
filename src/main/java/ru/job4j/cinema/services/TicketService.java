package ru.job4j.cinema.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketStore;

import java.util.List;

@Service
@ThreadSafe
public class TicketService {

    private final TicketStore store;

    public TicketService(TicketStore store) {
        this.store = store;
    }

    public void save(Ticket ticket) {
        store.save(ticket);
    }

    public List<Ticket> findTicketsByUserId(int id) {
        return store.findTicketsByUserId(id);
    }

    public List<Session> findAll() {
        return store.findAll();
    }
}
