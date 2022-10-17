package ru.job4j.cinema.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@ThreadSafe
@Slf4j
@RequiredArgsConstructor
public class UserStore {

    private final BasicDataSource pool;

    public Optional<User> add(User user) {
        Optional<User> res = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO users(username, email, phone, password) values (?, ?, ?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getPassword());
            statement.execute();
            try (ResultSet id = statement.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            res = Optional.of(user);
        } catch (SQLException e) {
            log.error("SQLException", e);
        }
        return res;
    }

    public Optional<User> findUserByUsernameAndPassword(String username, String password) {
        Optional<User> res = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users where username = ? and password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    res = Optional.of(createUser(resultSet));
                }
            }
        } catch (SQLException e) {
            log.error("SQLException", e);
        }
        return res;
    }

    public Optional<User> findUserByName(String username) {
        Optional<User> res = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users where username = ?")
        ) {
            ps.setString(1, username);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    res = Optional.of(createUser(it));
                }
            }
        } catch (Exception e) {
            log.error("SQLException", e);
        }
        return res;
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getString("password"));
    }

}
