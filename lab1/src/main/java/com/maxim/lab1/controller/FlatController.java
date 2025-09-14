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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlatController {

    DtoMapper mapper;
    FlatService flatService;
    RemovalService removalService;

    @PostMapping("/flat/create")
    public String createFlat(@Valid @ModelAttribute("flatDto") FlatDto flatDto,
                             BindingResult result,
                             @RequestParam(value = "link", required = false, defaultValue = "false") Boolean link) {
        if (result.hasErrors()) {
            return "create";
        }
        flatService.createFlat(mapper.toFlat(flatDto), link);
        return "redirect:/page";
    }

    @PostMapping("/flat/update")
    public String updateFlat(@Valid @Validated(ValidationGroups.Update.class) @ModelAttribute("flatDto") FlatDto flatDto,
                             BindingResult result,
                             @RequestParam(value = "link", required = false, defaultValue = "false") Boolean link,
                             Model model) {
        try {
            if (result.hasErrors()) {
                return "update";
            }
            flatService.updateFlat(mapper.toFlat(flatDto), link);
            return "redirect:/page";
        } catch (ValidationException e) {
            result.addError(new FieldError("flatDto", "id", e.getMessage()));
            model.addAttribute("flatDto", flatDto);
            return "update";
        }
    }

    @GetMapping("/entity/delete")
    public String deleteEntity(@RequestParam("entityType") String entityType, @RequestParam("id") Long id, Model model) {
        try {
            removalService.removeById(entityType.toLowerCase(), id);
            return "redirect:/page"; // Перенаправление на страницу успеха
        } catch (ValidationException e) {
            model.addAttribute("validationError", e.getMessage());
            return "delete"; // Имя Thymeleaf-шаблона
        }
    }

}
