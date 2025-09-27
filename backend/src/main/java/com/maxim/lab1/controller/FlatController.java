package com.maxim.lab1.controller;

import com.maxim.lab1.controller.dto.DtoMapper;
import com.maxim.lab1.controller.dto.FlatDto;
import com.maxim.lab1.controller.dto.ValidationGroups;
import com.maxim.lab1.service.FlatService;
import com.maxim.lab1.service.RemovalService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController("/flat")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlatController {

    DtoMapper mapper;
    FlatService flatService;
    RemovalService removalService;

    @GetMapping("/page")
    public Page<FlatDto> getFlats(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.ASC)
                                  Pageable pageable,
                                  @RequestParam(value = "name", required = false) String name) {
        return flatService.getPage(pageable, name).map(mapper::toFlatDto);
    }

    @PostMapping("/create")
    public void createFlat(@Valid FlatDto flatDto,
                             @RequestParam(value = "link", required = false, defaultValue = "false") Boolean link) {

        flatService.createFlat(mapper.toFlat(flatDto), link);
    }

    @PostMapping("/update")
    public void updateFlat(@Valid @Validated(ValidationGroups.Update.class) FlatDto flatDto,
                             @RequestParam(value = "link", required = false, defaultValue = "false") Boolean link) {

        flatService.updateFlat(mapper.toFlat(flatDto), link);
    }

    @DeleteMapping("/{entity}/{id}")
    public void deleteEntity(@PathVariable("entity") String entityType, @PathVariable("id") Long id) {
        removalService.deleteById(entityType.toLowerCase(), id);
    }

}
