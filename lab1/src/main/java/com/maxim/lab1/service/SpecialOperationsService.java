package com.maxim.lab1.service;

import com.maxim.lab1.controller.dto.DtoMapper;
import com.maxim.lab1.db.FlatDbService;
import com.maxim.lab1.db.model.FlatDao;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.House;
import com.maxim.lab1.model.Transport;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecialOperationsService {

    FlatDbService flatDbService;

    public long findCountByHouseGreaterThan(House house) {
        return flatDbService.findCountByHouseGreaterThan(house);
    }

    public List<Flat> findAllByNameStartingWith(String name) {
        return flatDbService.findAllByNameStartingWith(name);
    }

    public Set<Transport> distinctTransport() {
        return flatDbService.distinctTransport();
    }

    public Optional<Flat> findMostExpensive(Long id1, Long id2, Long id3) {
        return flatDbService.findMostExpensive(id1, id2, id3);
    }

    public long findTotalCost() {
        return flatDbService.findTotalCost();
    }
}
