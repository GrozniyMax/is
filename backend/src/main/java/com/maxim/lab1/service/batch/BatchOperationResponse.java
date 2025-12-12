package com.maxim.lab1.service.batch;

import com.maxim.lab1.model.Flat;

import java.util.List;

public record BatchOperationResponse(
        Long batchId,
        List<Flat> entities
) {
}
