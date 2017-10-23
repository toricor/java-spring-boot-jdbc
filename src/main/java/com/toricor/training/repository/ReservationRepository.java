package com.toricor.training.repository;

import com.toricor.training.domain.Reservation;
import com.toricor.training.domain.ReservationUserEvent;
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
public class ReservationRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    SimpleJdbcInsert insert;

    @PostConstruct
    public void init() {
        insert = new SimpleJdbcInsert((JdbcTemplate) jdbcTemplate.getJdbcOperations())
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private static final RowMapper<Reservation> reservationRowMapper = (rs, i) -> {
        Integer id       = rs.getInt("id");
        Integer user_id  = rs.getInt("user_id");
        Integer event_id = rs.getInt("event_id");
        return new Reservation(id, user_id, event_id);
    };

    private static final RowMapper<ReservationUserEvent> reservationRowMapperJoined = (rs, i) -> {
        Integer id       = rs.getInt("id");
        String user_name  = rs.getString("user_name");
        String event_name = rs.getString("event_name");
        return new ReservationUserEvent(id, user_name, event_name);
    };

    public List<ReservationUserEvent> findAllJoined() {
        List<ReservationUserEvent> reservations = jdbcTemplate.query(
                "SELECT reservation.id, user.name AS user_name, event.title AS event_name FROM reservation " +
                        "INNER JOIN user ON user.id = reservation.user_id " +
                        "INNER JOIN event ON event.id = reservation.event_id " +
                        "ORDER BY reservation.id",
                reservationRowMapperJoined);
        return reservations;
    }

    public List<Reservation> findAll() {
        List<Reservation> reservations = jdbcTemplate.query(
                "SELECT * FROM reservation",
                reservationRowMapper);
        return reservations;
    }

    public Reservation findOne(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.queryForObject(
                "SELECT id, user_id, event_id FROM reservation WHERE id=:id",
                param,
                reservationRowMapper);
    }

    public Reservation save(Reservation reservation) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(reservation);
        if (reservation.getId() == null) {
            Number key = insert.executeAndReturnKey(param);
            reservation.setId(key.intValue());
        } else {
            jdbcTemplate.update("UPDATE reservation SET user_id=:user_id, event_id=:event_id WHERE id=:id",
                    param);
        }
        return reservation;
    }

    public void delete(Integer id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        jdbcTemplate.update("DELETE FROM reservation WHERE id=:id",
                param);
    }
}