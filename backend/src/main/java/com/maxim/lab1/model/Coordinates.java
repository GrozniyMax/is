package com.maxim.lab1.model;

import lombok.With;

/**
 *
 */
@With
public record Coordinates(
        Point first,
        Point second) {
}
