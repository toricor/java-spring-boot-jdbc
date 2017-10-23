package com.toricor.training.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reservation {
    private Integer id;
    private Integer userId;
    private Integer eventId;
}
