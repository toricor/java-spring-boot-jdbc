package com.toricor.training.service;

import com.toricor.training.domain.Reservation;
import com.toricor.training.domain.ReservationUserEvent;
import com.toricor.training.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReservationService {
    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public List<ReservationUserEvent> findAllJoined() {
        return reservationRepository.findAllJoined();
    }

    public Reservation findOne(Integer id) {
        return reservationRepository.findOne(id);
    }

    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation update(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void delete(Integer id) {
        reservationRepository.delete(id);
    }
}
