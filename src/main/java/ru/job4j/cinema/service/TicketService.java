package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@ThreadSafe
public class TicketService {

    private final TicketStore store;
    private final List<Integer> rowNumbers;
    private final List<Integer> cellNumbers;

    public TicketService(TicketStore store,
                         @Value("${position.row.count}") int rowCount,
                         @Value("${position.cell.count}") int cellCount) {
        this.store = store;
        this.rowNumbers = IntStream.rangeClosed(1, rowCount).boxed().toList();
        this.cellNumbers = IntStream.rangeClosed(1, cellCount).boxed().toList();
    }

    public Optional<Ticket> save(Ticket ticket) {
        return store.save(ticket);
    }


    public Optional<Ticket> findTicketByRowPosition(int row, int cell, int id) {
        return store.findTicketByRowPosition(row, cell, id);
    }

    public List<Integer> rowList() {
        return new ArrayList<>(rowNumbers);
    }

    public List<Integer> cellList() {
        return new ArrayList<>(cellNumbers);
    }

}
