package com.toricor.demo.repository;

import com.toricor.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@Transactional
public class UserRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    SimpleJdbcInsert insert;

    @PostConstruct
    public void init() {
        insert = new SimpleJdbcInsert((JdbcTemplate) jdbcTemplate.getJdbcOperations())
                .withTableName("user")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<User> userRowMapper = (rs, i) -> {
        Integer id = rs.getInt("id");
        String Name = rs.getString("name");
        return new User(id, Name);
    };

    public List<User> findAll() {
        List<User> users = jdbcTemplate.query(
                "SELECT id, name FROM user ORDER BY id",
                userRowMapper);
        return users;
    }

    public User findOne(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.queryForObject(
                "SELECT id, name FROM user WHERE id=:id",
                param,
                userRowMapper);
    }

    public User save(User user) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        if (user.getId() == null) {
            Number key = insert.executeAndReturnKey(param);
            user.setId(key.intValue());
        } else {
            jdbcTemplate.update("UPDATE user SET name=:name WHERE id=:id",
                    param);
        }
        return user;
    }

    public void delete(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        jdbcTemplate.update("DELETE FROM user WHERE id=:id",
                param);
    }
}
