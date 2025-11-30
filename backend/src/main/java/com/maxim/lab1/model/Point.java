package com.maxim.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;


@With
public record Point(
        double x,
        double y
) {

}
