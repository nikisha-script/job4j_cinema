package ru.job4j.cinema.store;

import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.filter.PasswordEncoder;
import ru.job4j.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@ThreadSafe
@Slf4j
public class UsersStore {

    private BasicDataSource pool;

    public UsersStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> res = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection
                     .prepareStatement("INSERT INTO users(username, email, phone, password) values (?, ?, ?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, PasswordEncoder.passwordOfDef(user.getPassword()));
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

    public Optional<User> findUser(User user) {
        Optional<User> res = Optional.empty();
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users where username = ? and password = ?")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    res = Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error("SQLException", e);
        }
        return res;
    }

    public User findUserByName(String username) {
        User res = new User();
        try (Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users where username = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    res.setId(resultSet.getInt("id"));
                    res.setUsername(resultSet.getString("username"));
                    res.setEmail(resultSet.getString("email"));
                    res.setPhone(resultSet.getString("phone"));
                    res.setPassword(resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
