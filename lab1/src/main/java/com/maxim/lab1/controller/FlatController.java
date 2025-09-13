package com.maxim.lab1.controller;

import com.maxim.lab1.controller.dto.DtoMapper;
import com.maxim.lab1.controller.dto.FlatDto;
import com.maxim.lab1.service.FlatService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/flat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlatController {

    DtoMapper mapper;
    FlatService service;

    @PostMapping("/")
    public void createFlat(@Valid @ModelAttribute("flat") FlatDto flatDto, @RequestParam("link") boolean link) {
        service.createFlat(mapper.toFlat(flatDto), link);
    }

    @PatchMapping("/")
    public void updateFlat(@Valid @ModelAttribute("flat") FlatDto flatDto, @RequestParam("link") boolean link) {
        service.updateFlat(mapper.toFlat(flatDto), link);
    }

    @DeleteMapping("/{id}")
    public void deleteFlat(@PathVariable("id") Long id) {
        service.deleteFlat(id);
    }

    @GetMapping("/page")
    public Page<FlatDto> getFlats(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
                                      Pageable pageable,
                                  @RequestParam(value = "name", required = false) String name) {
        return service.getPage(pageable, name).map(mapper::toFlatDto);
    }
}
