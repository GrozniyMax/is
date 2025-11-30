package com.maxim.lab1.controller;


import com.maxim.is.generated.dto.FlatCreateDto;
import com.maxim.is.generated.dto.FlatDto;
import com.maxim.is.generated.dto.FlatsPageGet200Response;
import com.maxim.is.generated.openapi.api.FlatApi;
import com.maxim.lab1.service.FlatRegistry;
import com.maxim.lab1.service.RemovalService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FlatController implements FlatApi {

    DtoMapper mapper;
    FlatRegistry flatRegistry;
    RemovalService removalService;


    @Override
    public ResponseEntity<Void> flatsCreatePost(FlatCreateDto flatCreateDto, Boolean link) {
        flatRegistry.createFlat(
                mapper.toFlat(flatCreateDto),
                link
        );
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> flatsIdDelete(Long id) {
        flatRegistry.deleteFlat(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<FlatsPageGet200Response> flatsPageGet(String name, Integer page, Integer size, String sort) {
        var resultPage = flatRegistry.getPage(PageRequest.of(page, size, resolveSort(sort)), name);
        return ResponseEntity.ok(new FlatsPageGet200Response()
                .content(resultPage.map(mapper::toFlatDto).getContent())
                .totalPages(resultPage.getTotalPages())
                .number(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements((int) resultPage.getTotalElements())
        );
    }

    @Override
    public ResponseEntity<Void> flatsUpdatePost(FlatDto flatDto, Boolean link) {
        flatRegistry.updateFlat(mapper.toFlat(flatDto), link);

        return ResponseEntity.ok().build();
    }

    private Sort resolveSort(String sort) {
        String[] split = sort.split(",");
        return Sort.by(Sort.Direction.fromString(split[1]), split[0]);
    }
}
