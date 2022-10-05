package ru.job4j.cinema.services;

import lombok.RequiredArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
@RequiredArgsConstructor
public class TicketService {

    private final TicketStore store;
    private final int rows = 10;
    private final int cells = 10;

    public Optional<Ticket> save(Ticket ticket) {
        return store.save(ticket);
    }

    public List<Ticket> findTicketsByUserId(int id) {
        return store.findTicketsByUserId(id);
    }

    public Optional<Ticket> findTicketByRowPosition(int row, int cell, int id) {
        return store.findTicketByRowPosition(row, cell, id);
    }

    public List<Integer> rowsList() {
        List<Integer> lists = new ArrayList<>();
        for (int index = 1; index <= rows; index++) {
            lists.add(index);
        }
        return lists;
    }

    public List<Integer> cellsList() {
        List<Integer> lists = new ArrayList<>();
        for (int index = 1; index <= cells; index++) {
            lists.add(index);
        }
        return lists;
    }
}
