package com.maxim.lab1.service;

import com.maxim.lab1.db.repository.CoordinatesRepository;
import com.maxim.lab1.db.repository.FlatRepository;
import com.maxim.lab1.db.repository.HouseRepository;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RemovalService {

    FlatRepository flatRepository;
    CoordinatesRepository coordinatesRepository;
    HouseRepository houseRepository;

    public void removeById(String entityType, Long id) {
        try {
            switch (entityType) {
                case "flat" -> flatRepository.deleteById(id);
                case "coordinates" -> coordinatesRepository.deleteById(id);
                case "house" -> houseRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new ValidationException("Не могу удалить " + entityType + " с id " + id);
        }
    }
}
