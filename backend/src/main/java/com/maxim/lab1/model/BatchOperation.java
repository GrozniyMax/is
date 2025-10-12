package com.maxim.lab1.model;

import lombok.AllArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;

public record BatchOperation(
        String user,
        LocalDateTime creationData,
        boolean success
) {
}
