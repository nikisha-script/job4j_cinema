package ru.job4j.cinema.store;

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

@Slf4j
@Repository
public class FilmsStore {

    private BasicDataSource pool;

    public FilmsStore(BasicDataSource pool) {
        this.pool = pool;
    }

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


    public boolean delete(int id) {
        boolean res = false;
        try (Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from films where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            res = true;
        } catch (SQLException e) {
            log.error("SQLException in delete method", e);
        }
        return res;
    }


    public boolean update(Film value) {
        boolean res = false;
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update films set name = ?, description = ?, img = ? where id = ?")) {
            preparedStatement.setString(1, value.getName());
            preparedStatement.setString(2, value.getDescription());
            preparedStatement.setBytes(3, value.getImg());
            preparedStatement.setInt(4, value.getId());
            preparedStatement.execute();
            res = true;
        } catch (SQLException e) {
            log.error("SQLException in update method", e);
        }
        return res;
    }

    public List<Film> findAll() {
        List<Film> result = new ArrayList<>();
        try (Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from films");
        ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                result.add(new Film(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBytes("img")));
            }
        } catch (SQLException e) {
            log.error("SQLException in findAll method", e);
        }
        return result;
    }


    public Film findById(int id) {
        Film res = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM films WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    res = new Film(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            it.getBytes("img"));
                }
            }
        } catch (Exception e) {
            log.error("SQLException", e);
        }
        return res;
    }
}
