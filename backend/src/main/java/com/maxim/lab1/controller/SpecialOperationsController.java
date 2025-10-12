package com.maxim.lab1.controller;

import com.maxim.api.model.FlatDto;
import com.maxim.api.model.HouseDto;
import com.maxim.lab1.service.SpecialOperationsService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController("/operations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecialOperationsController {

    SpecialOperationsService specialOperationsService;

    DtoMapper mapper;

    @PostMapping("/findCountByHouseGreaterThan")
    public long findCountByHouseGreaterThan(@Valid HouseDto house) {
        return specialOperationsService.findCountByHouseGreaterThan(mapper.toHouse(house));
    }

    @GetMapping("/findMostExpensive")
    public FlatDto findMostExpensive(
            @RequestParam("id1") Long id1,
            @RequestParam("id2") Long id2,
            @RequestParam("id3") Long id3) {
        return specialOperationsService.findMostExpensive(id1, id2, id3).map(mapper::toFlatDto).orElse(null);
    }

    @GetMapping("/findTotalCost")
    public long findTotalCost() {
        return specialOperationsService.findTotalCost();
    }
}
