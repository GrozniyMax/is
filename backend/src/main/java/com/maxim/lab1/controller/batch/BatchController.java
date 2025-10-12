package com.maxim.lab1.controller.batch;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.api.model.FlatDto;
import com.maxim.lab1.controller.DtoMapper;
import com.maxim.lab1.service.BatchUpdateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@RestController("/batch")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BatchController {

    DtoMapper dtoMapper;

    BatchUpdateService batchUpdateService;

    ObjectMapper objectMapper;

    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file, @RequestParam("user") String user) throws IOException {
        batchUpdateService.saveAll(
                read(file).stream().map(dtoMapper::toFlat).toList(),
                user
        );

    }

    @GetMapping("/{user}")
    public List<?> getAllByUser(@PathVariable("user") String user) {
        // TODO add dto
        return batchUpdateService.getAllByUser(user)
                .stream().map(dtoMapper::toBatchOperationDto);
    }


    private List<FlatDto> read(MultipartFile file) throws IOException {
        return objectMapper.readValue(file.getBytes(), new TypeReference<List<FlatDto>>() {});
    }
}
