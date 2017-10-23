package com.toricor.demo.repository;

import com.toricor.demo.domain.Event;
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
import java.sql.Timestamp;

@Repository
@Transactional
public class EventRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    SimpleJdbcInsert insert;

    @PostConstruct
    public void init() {
        insert = new SimpleJdbcInsert((JdbcTemplate) jdbcTemplate.getJdbcOperations())
                .withTableName("event")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<Event> eventRowMapper = (rs, i) -> {
        Integer id              = rs.getInt("id");
        String title            = rs.getString("title");
        String description      = rs.getString("description");
        Integer author          = rs.getInt("author");
        String place            = rs.getString("place");
        Integer participants    = rs.getInt("participants");
        Integer maxParticipants = rs.getInt("max_participants");
        Timestamp createdAt     = rs.getTimestamp("created_at");
        Timestamp publishedAt   = rs.getTimestamp("published_at");
        return new Event(id, title, description, author, place, participants, maxParticipants, createdAt, publishedAt);
    };

    public List<Event> findAll() {
        List<Event> events = jdbcTemplate.query(
                "SELECT * FROM event ORDER BY id",
                eventRowMapper);
        return events;
    }

    public Event findOne(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.queryForObject(
                "SELECT * FROM event WHERE id=:id",
                param,
                eventRowMapper);
    }

    public Event save(Event event) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(event);
        if (event.getId() == null) {
            Number key = insert.executeAndReturnKey(param);
            event.setId(key.intValue());
        } else {
            jdbcTemplate.update("UPDATE event SET title=:title WHERE id=:id",
                    param);
        }
        return event;
    }

    public void delete(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        jdbcTemplate.update("DELETE FROM event WHERE id=:id",
                param);
    }
}