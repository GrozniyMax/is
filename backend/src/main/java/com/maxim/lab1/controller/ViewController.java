package com.maxim.lab1.controller;

import com.maxim.lab1.controller.dto.DtoMapper;
import com.maxim.lab1.controller.dto.FlatDto;
import com.maxim.lab1.service.FlatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ViewController {

    FlatService flatService;
    DtoMapper mapper;

    private record SortParams(String field, String type) {

        public SortParams next() {
            if ("desc".equals(type)) {
                return new SortParams(field, "asc");
            } else {
                return new SortParams(field, "acs");
            }
        }

        public String toSort() {
            return field + "," + type;
        }
    }

    @GetMapping("/page")
    public String getFlats(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.ASC)
                           Pageable pageable,
                           @RequestParam(value = "name", required = false) String name,
                           Model model) {
        var result = flatService.getPage(pageable, name).map(mapper::toFlatDto);

        var sortParam = pageable.getSort().stream()
                .map(order -> new SortParams(order.getProperty(), order.getDirection().name().toLowerCase()))
                .findFirst()
                .orElse(new SortParams("id", "asc"));

        model.addAttribute("name", name);
        model.addAttribute("flats", result.getContent());
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        model.addAttribute("total", result.getTotalPages());
        model.addAttribute("sort", sortParam);

        return "main";
    }

    @GetMapping("/create")
    public String getMainPage(Model model) {
        model.addAttribute("flatDto", new FlatDto());
        return "create";
    }

    @GetMapping("/update")
    public String getUpdatePage(Model model) {
        model.addAttribute("flatDto", new FlatDto());
        return "update";
    }

    @GetMapping("/delete")
    public String getDeletePage() {
        return "delete";
    }

    @GetMapping("/specials")
    public String getSpecialsPage() {
        return "specials";
    }


}
