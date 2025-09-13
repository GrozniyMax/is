package com.maxim.lab1.service;

import com.maxim.lab1.db.FlatDbService;
import com.maxim.lab1.db.model.FlatDao;
import com.maxim.lab1.model.Flat;
import jakarta.persistence.TableGenerator;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FlatService {

    FlatDbService flatDbService;

    @Transactional
    public void createFlat(Flat flat, boolean link) {
        flat = flat.withId(null); // Чтобы точно создать новую запись
        flatDbService.save(flat, link);
    }

    @Transactional
    public void updateFlat(Flat flat, boolean link) {
        flatDbService.save(flat, link);
    }

    @Transactional
    public void deleteFlat(Long flatId) {
        flatDbService.deleteById(flatId);
    }

    public Page<Flat> getPage(Pageable pageable, String name) {
        if (name == null) {
            return flatDbService.findAll(pageable);
        } else {
            return flatDbService.findAllByName(name, pageable);
        }
    }
}
