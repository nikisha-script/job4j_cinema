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

    public List<Session> findAll() {
        List<Session> result = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from session");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.add(new Session(resultSet.getInt("id"),
                        resultSet.getInt("pos")));
            }
        } catch (SQLException e) {
            log.error("SQLException in findAll method", e);
        }
        return result;
    }
}
