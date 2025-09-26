package com.maxim.lab1.service;

import com.maxim.lab1.db.RemovalDbService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemovalService {

    RemovalDbService removalDbService;

    public void deleteById(String entityType, Long id) {
        removalDbService.removeById(entityType, id);
    }
}
