package com.maxim.lab1.controller;


import com.maxim.is.generated.dto.FlatDto;
import com.maxim.is.generated.dto.HouseDto;
import com.maxim.is.generated.openapi.api.SpecialOperationsApi;
import com.maxim.lab1.service.SpecialOperationsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecialOperationsController implements SpecialOperationsApi {

    SpecialOperationsService specialOperationsService;

    DtoMapper mapper;

    @Override
    public ResponseEntity<Long> findCountByHouseGreaterThanPost(HouseDto houseDto) {
        return ResponseEntity.ok(specialOperationsService.findCountByHouseGreaterThan(mapper.toHouse(houseDto)));
    }

    @Override
    public ResponseEntity<FlatDto> findMostExpensiveGet(Long id1, Long id2, Long id3) {
        return specialOperationsService.findMostExpensive(id1, id2, id3)
                .map(mapper::toFlatDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Long> findTotalCostGet() {
        return ResponseEntity.ok(specialOperationsService.findTotalCost());
    }
}
