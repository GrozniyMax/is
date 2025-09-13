package com.maxim.lab1.controller;

import com.maxim.lab1.dto.FlatDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/flat")
public class FlatController {

    @PostMapping("/")
    public void createFlat(@Valid @ModelAttribute("flat") FlatDto flatDto) {

    }

    @PatchMapping("/")
    public void updateFlat(@Valid @ModelAttribute("flat") FlatDto flatDto) {

    }

    @DeleteMapping("/{id}")
    public void deleteFlat(@PathVariable("id") Long id) {

    }

    @GetMapping("/")
    public void getFlats(@RequestParam("offset") int page, @RequestParam("limit") int size) {

    }
}
