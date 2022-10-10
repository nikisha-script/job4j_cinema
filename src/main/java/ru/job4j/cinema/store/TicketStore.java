package ru.job4j.cinema.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@ThreadSafe
@Slf4j
@RequiredArgsConstructor
public class TicketStore {

    private final BasicDataSource pool;


    public Optional<Ticket> save(Ticket ticket) {
        Optional<Ticket> rsl = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into ticket(pos_row, user_id, film_id, cell) values (?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, ticket.getRow());
                preparedStatement.setInt(2, ticket.getUserId());
                preparedStatement.setInt(3, ticket.getFilmId());
                preparedStatement.setInt(4, ticket.getCell());
                preparedStatement.execute();
                try (ResultSet id = preparedStatement.getGeneratedKeys()) {
                    if (id.next()) {
                        ticket.setId(id.getInt(1));
                    }
                }
                rsl = Optional.of(ticket);
        } catch (SQLException e) {
            log.error("SQLException in method buyTicket", e);
        }

        return rsl;
    }

    public List<Ticket> findTicketsByUserId(int id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from ticket where user_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tickets.add(createTicket(rs));
                }
            }
        } catch (SQLException e) {
            log.error("SQLException in method findTicketsByUserId", e);
        }
        return tickets;
    }

    public Optional<Ticket> findTicketByRowPosition(int row, int cell, int filmId) {
        Optional<Ticket> ticket = Optional.empty();
        try (Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from ticket where pos_row = ? and cell = ? and film_id = ?")) {
            preparedStatement.setInt(1, row);
            preparedStatement.setInt(2, cell);
            preparedStatement.setInt(3, filmId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Ticket temp = new Ticket();
                    temp.setId(resultSet.getInt("id"));
                    temp.setRow(resultSet.getInt("pos_row"));
                    temp.setCell(resultSet.getInt("cell"));
                    temp.setUserId(resultSet.getInt("user_id"));
                    temp.setFilmId(resultSet.getInt("film_id"));
                    ticket = Optional.of(temp);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException in method findTicketByRowPosition", e);
        }
        return ticket;
    }

    private Ticket createTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(resultSet.getInt("id"),
                resultSet.getInt("pos_row"),
                resultSet.getInt("cell"),
                resultSet.getInt("user_id"),
                resultSet.getInt("film_id"));
    }

}
