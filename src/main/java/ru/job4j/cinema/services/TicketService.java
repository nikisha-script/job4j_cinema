package ru.job4j.cinema.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketStore;

import java.util.List;
import java.util.Optional;

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

    public Optional<Ticket> findTicketByRowPosition(int row, int id) {
        return store.findTicketByRowPosition(row, id);
    }
}
