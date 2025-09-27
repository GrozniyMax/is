package com.maxim.lab1.controller;

import com.maxim.lab1.controller.dto.DtoMapper;
import com.maxim.lab1.controller.dto.FlatDto;
import com.maxim.lab1.controller.dto.HouseDto;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.Transport;
import com.maxim.lab1.service.SpecialOperationsService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
