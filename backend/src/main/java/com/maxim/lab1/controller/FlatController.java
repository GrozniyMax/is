package com.maxim.lab1.controller;

import com.maxim.api.model.FlatDto;

import com.maxim.lab1.service.FlatRegistry;
import com.maxim.lab1.service.RemovalService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController("/flat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlatController {

    DtoMapper mapper;
    FlatRegistry flatRegistry;
    RemovalService removalService;

    @GetMapping("/page")
    public Page<FlatDto> getFlats(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.ASC)
                                  Pageable pageable,
                                  @RequestParam(value = "name", required = false) String name) {
        return flatRegistry.getPage(pageable, name).map(mapper::toFlatDto);
    }

    @PostMapping("/create")
    public void createFlat(@Valid FlatDto flatDto,
                             @RequestParam(value = "link", required = false, defaultValue = "false") Boolean link) {

        flatRegistry.createFlat(mapper.toFlat(flatDto), link);
    }

    @PostMapping("/update")
    public void updateFlat(@Valid FlatDto flatDto,
                             @RequestParam(value = "link", required = false, defaultValue = "false") Boolean link) {

        flatRegistry.updateFlat(mapper.toFlat(flatDto), link);
    }

    @DeleteMapping("/{entity}/{id}")
    public void deleteEntity(@PathVariable("entity") String entityType, @PathVariable("id") Long id) {
        removalService.deleteById(entityType.toLowerCase(), id);
    }

}
