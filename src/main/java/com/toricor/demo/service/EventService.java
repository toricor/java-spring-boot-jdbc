package com.toricor.demo.service;

import com.toricor.demo.domain.Event;
import com.toricor.demo.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventService {
    @Autowired
    EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findOne(Integer id) {
        return eventRepository.findOne(id);
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public Event update(Event event) {
        return eventRepository.save(event);
    }

    public void delete(Integer id) {
        eventRepository.delete(id);
    }
}
