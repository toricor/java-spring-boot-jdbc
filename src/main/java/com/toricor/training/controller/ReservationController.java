package com.toricor.training.controller;

import com.toricor.training.domain.Reservation;
import com.toricor.training.domain.ReservationUserEvent;
import com.toricor.training.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    // 予約全件取得
    @RequestMapping(method = RequestMethod.GET)
    List<Reservation> getReservations() {
        List<Reservation> reservations = reservationService.findAll();
        return reservations;
    }

    // 予約全件取得(Joined)
    @RequestMapping(value = "joined",method = RequestMethod.GET)
    List<ReservationUserEvent> getReservationsJoined() {
        List<ReservationUserEvent> reservations = reservationService.findAllJoined();
        return reservations;
    }
    // 予約1件取得
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    Reservation getReservation(@PathVariable Integer id) {
        Reservation reservation = reservationService.findOne(id);
        return reservation;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Reservation postReservations(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    Reservation putReservation(@PathVariable Integer id, @RequestBody Reservation reservation) {
        reservation.setId(id);
        return reservationService.update(reservation);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteReservation(@PathVariable Integer id) {
        reservationService.delete(id);
    }
}
