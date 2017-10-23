package com.toricor.training.controller;

import com.toricor.training.domain.Event;
import com.toricor.training.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/events")
public class EventController {
    @Autowired
    EventService eventService;

    // イベント全件取得
    @RequestMapping(method = RequestMethod.GET)
    List<Event> getEvents() {
        List<Event> events = eventService.findAll();
        return events;
    }

    // イベント1件取得
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    Event getEvent(@PathVariable Integer id) {
        Event event = eventService.findOne(id);
        return event;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    Event postEvents(@RequestBody Event event) {
        return eventService.create(event);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    Event putEvent(@PathVariable Integer id, @RequestBody Event event) {
        event.setId(id);
        return eventService.update(event);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEvent(@PathVariable Integer id) {
        eventService.delete(id);
    }
}
