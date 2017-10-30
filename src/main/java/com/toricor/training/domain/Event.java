package com.toricor.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Event {
    private Integer id;
    private String title;

    @JsonIgnore
    private String description;
    private Integer author;
    private String place;
    private Integer participants;
    private Integer maxParticipants;
    private Timestamp createdAt;
    private Timestamp publishedAt;
}
