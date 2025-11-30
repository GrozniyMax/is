package com.maxim.lab1.model;

import lombok.AllArgsConstructor;
import lombok.With;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record BatchOperation(
        @Nullable
        Long id,
        String user,
        ZonedDateTime creationData,
        boolean success
) {
}
