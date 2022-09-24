package ru.job4j.cinema.store;

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
public class TicketStore {

    private BasicDataSource pool;

    public TicketStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public void save(Ticket ticket) {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into ticket(pos_row, user_id, film_id) values (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, ticket.getRow());
                preparedStatement.setInt(2, ticket.getUserId());
                preparedStatement.setInt(3, ticket.getFilmId());
                preparedStatement.execute();
                try (ResultSet id = preparedStatement.getGeneratedKeys()) {
                    if (id.next()) {
                        ticket.setId(id.getInt(1));
                    }
                }
        } catch (SQLException e) {
            log.error("SQLException in method buyTicket", e);
        }
    }

    public List<Ticket> findTicketsByUserId(int id) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("select * from ticket where user_id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tickets.add(new Ticket(rs.getInt("id"),
                            rs.getInt("pos_row"),
                            rs.getInt("user_id"),
                            rs.getInt("film_id")));
                }
            }
        } catch (SQLException e) {
            log.error("SQLException in method findTicketsByUserId", e);
        }
        return tickets;
    }

    public Optional<Ticket> findTicketByRowPosition(int row, int id) {
        Optional<Ticket> ticket = Optional.empty();
        Ticket temp = new Ticket();
        try (Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from ticket where pos_row = ? and film_id = ?")) {
            preparedStatement.setInt(1, row);
            preparedStatement.setInt(2, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    temp.setId(resultSet.getInt("id"));
                    temp.setId(resultSet.getInt("pos_row"));
                    temp.setId(resultSet.getInt("user_id"));
                    temp.setId(resultSet.getInt("film_id"));
                }
            }
            ticket = Optional.of(temp);
        } catch (SQLException e) {
            log.error("SQLException in method findTicketByRowPosition", e);
        }
        return ticket;
    }

}
