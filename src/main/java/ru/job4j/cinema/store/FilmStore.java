package ru.job4j.cinema.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FilmStore {

    private final BasicDataSource pool;

    public Film add(Film value) {

        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into films(name, description, img) values (?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, value.getName());
            preparedStatement.setString(2, value.getDescription());
            preparedStatement.setBytes(3, value.getImg());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    value.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            log.error("SQLException in add method", e);
        }
        return value;
    }



    public List<Film> findAll() {
        List<Film> result = new ArrayList<>();
        try (Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from films");
        ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.add(createFilm(resultSet));
            }
        } catch (SQLException e) {
            log.error("SQLException in findAll method", e);
        }
        return result;
    }


    public Optional<Film> findById(int id) {
        Optional<Film> res = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM films WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    res = Optional.of(createFilm(it));
                }
            }
        } catch (Exception e) {
            log.error("SQLException", e);
        }
        return res;
    }

    private Film createFilm(ResultSet resultSet) throws SQLException {
        return new Film(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBytes("img"));
    }
}
