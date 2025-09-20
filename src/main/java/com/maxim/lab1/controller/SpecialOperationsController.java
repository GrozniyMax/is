package com.maxim.lab1.controller;

import com.maxim.lab1.controller.dto.DtoMapper;
import com.maxim.lab1.controller.dto.FlatDto;
import com.maxim.lab1.controller.dto.HouseDto;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.Transport;
import com.maxim.lab1.service.SpecialOperationsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpecialOperationsController {

    SpecialOperationsService specialOperationsService;

    DtoMapper mapper;

    @PostMapping("/findCountByHouseGreaterThan")
    public String findCountByHouseGreaterThan(@ModelAttribute HouseDto house, Model model) {
        var result = specialOperationsService.findCountByHouseGreaterThan(mapper.toHouse(house));
        model.addAttribute("count", result);
        return "specials";
    }

    @GetMapping("/findAllByNameStartingWith")
    public String findAllByNameStartingWith(@RequestParam("name") String name, Model model) {
        var result = specialOperationsService.findAllByNameStartingWith(name).stream().map(mapper::toFlatDto).toList();
        model.addAttribute("flats", result);
        return "specials";
    }

    @GetMapping("/distinctTransport")
    public String distinctTransport(Model model) {
        var result = specialOperationsService.distinctTransport();
        model.addAttribute("transports", result);
        return "specials";
    }

    @GetMapping("/findMostExpensive")
    public String findMostExpensive(
            @RequestParam("id1") Long id1,
            @RequestParam("id2") Long id2,
            @RequestParam("id3") Long id3,
            Model model) {
        var result = specialOperationsService.findMostExpensive(id1, id2, id3).map(mapper::toFlatDto);
        model.addAttribute("flat", result.orElse(null));
        return "specials";
    }

    @GetMapping("/findTotalCost")
    public String findTotalCost(Model model) {
        var result = specialOperationsService.findTotalCost();
        model.addAttribute("totalCost", result);
        return "specials";
    }
}
