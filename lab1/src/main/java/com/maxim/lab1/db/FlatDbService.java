package com.maxim.lab1.db;

import com.maxim.lab1.db.model.DaoMapper;
import com.maxim.lab1.db.model.FlatDao;
import com.maxim.lab1.db.model.HouseDao;
import com.maxim.lab1.db.repository.CoordinatesRepository;
import com.maxim.lab1.db.repository.FlatRepository;
import com.maxim.lab1.db.repository.HouseRepository;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.House;
import com.maxim.lab1.model.Transport;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FlatDbService {

    DaoMapper mapper;

    FlatRepository flatRepository;
    CoordinatesRepository coordinatesRepository;
    HouseRepository houseRepository;

    @Transactional
    public Flat save(Flat flat, boolean link) {
        var dao = mapper.toFlatDao(flat);

        if (link) {
            link(dao);
        } else {
            notLink(dao);
        }

        return mapper.toFlat(flatRepository.save(dao));
    }

    @Transactional
    public void deleteById(Long id) {
        flatRepository.deleteById(id);
    }

    public Optional<Flat> findById(Long id) {
        return flatRepository.findById(id).map(mapper::toFlat);
    }

    public Page<Flat> findAllByName(String name, Pageable pageable) {
        return flatRepository.findAllByName(name, pageable).map(mapper::toFlat);
    }

    public Page<Flat> findAll(Pageable pageable) {
        return flatRepository.findAll(pageable).map(mapper::toFlat);
    }

    public long findCountByHouseGreaterThan(House house) {
        return flatRepository.findCountByHouseGreaterThan(mapper.toHouseDao(house));
    }

    public List<Flat> findAllByNameStartingWith(String name) {
        return flatRepository.findAllByNameStartingWith(name).map(mapper::toFlat).toList();
    }

    public Set<Transport> distinctTransport() {
        return flatRepository.distinctTransport();
    }

    public Optional<Flat> findMostExpensive(Long id1, Long id2, Long id3) {
        return Stream.of(id1, id2, id3)
                .map(flatRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .max(Comparator.comparing(FlatDao::getPrice))
                .map(mapper::toFlat);
    }

    public long findTotalCost() {
        return flatRepository.getTotalCost();
    }



    private void link(FlatDao flat) {
        coordinatesRepository.findByXAndY(flat.getCoordinatesDao().getX(), flat.getCoordinatesDao().getY())
                .ifPresent(flat::setCoordinatesDao);

        houseRepository.findByNameAndYearAndNumberOfFlatsOnFloorAndNumberOfLifts(
                flat.getHouseDao().getName(),
                flat.getHouseDao().getYear(),
                flat.getHouseDao().getNumberOfFlatsOnFloor(),
                flat.getHouseDao().getNumberOfLifts()
        ).ifPresent(flat::setHouseDao);
    }

    private void notLink(FlatDao flat) {
        // Чтобы JPA точно посчитал новыми объекты
        flat.getCoordinatesDao().setId(null);
        flat.getHouseDao().setId(null);
    }

}
