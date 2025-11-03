package com.maxim.lab1.model;

import lombok.AllArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record BatchOperation(
        String user,
        ZonedDateTime creationData,
        boolean success
) {
}
