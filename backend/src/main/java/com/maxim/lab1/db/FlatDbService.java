package com.maxim.lab1.db;

import com.maxim.lab1.db.model.TpcStatus;
import com.maxim.lab1.db.model.mapping.DaoMapper;
import com.maxim.lab1.db.model.FlatDao;
import com.maxim.lab1.db.repository.FlatRepository;
import com.maxim.lab1.db.repository.HouseRepository;
import com.maxim.lab1.model.Flat;
import com.maxim.lab1.model.House;
import com.maxim.lab1.model.Transport;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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
    HouseRepository houseRepository;


    @Transactional
    public Flat prepare(Flat flat, boolean link) {
        return save(flat, link, TpcStatus.PREPARED);
    }

    @Transactional
    public Flat commit(Flat flat, boolean link) {
        return save(flat, link, TpcStatus.COMMITED);
    }

    @Transactional
    public Flat save(Flat flat, boolean link) {
        return commit(flat, link);
    }

    @Transactional
    public List<Flat> prepareAll(List<Flat> flats) {
        return saveAll(flats, TpcStatus.PREPARED);
    }

    @Transactional
    public List<Flat> commitAll(List<Flat> flats) {
        return saveAll(flats, TpcStatus.COMMITED);
    }

    @Transactional
    public List<Flat> saveAll(List<Flat> flats) {
        return commitAll(flats);
    }

    private FlatDao save(FlatDao dao, boolean link) {
        if (link) {
            link(dao);
        } else {
            notLink(dao);
        }

        return flatRepository.save(dao);
    }

    public Flat save(Flat flat, boolean link, TpcStatus tpcStatus) {
        var dao = mapper.toFlatDao(flat);
        dao.setStatus(tpcStatus);
        return mapper.toFlat(save(dao, link));
    }

    private List<Flat> saveAll(List<Flat> flats, TpcStatus tpcStatus) {
        var entities = flats.stream()
                .map(mapper::toFlatDao)
                .peek(flatDao -> flatDao.setId(null))
                .peek(this::notLink)
                .peek(dao -> dao.setStatus(tpcStatus))
                .toList();

        flatRepository.saveAll(entities);

        return entities.stream()
                .map(mapper::toFlat)
                .toList();
    }

    @Transactional
    public void deleteById(Long id) {
        flatRepository.deleteById(id);
    }

    public Optional<Flat> findById(Long id) {
        return flatRepository.findById(id).map(mapper::toFlat);
    }

    public Page<Flat> findAllByName(String name, Pageable pageable) {
        return flatRepository.findAllByNameAndTpcStatus(name, pageable, TpcStatus.COMMITED).map(mapper::toFlat);
    }

    public Page<Flat> findAll(Pageable pageable) {
        return flatRepository.findAll(pageable).map(mapper::toFlat);
    }

    public Optional<Flat> getFirstByHouseId(Long houseId) {
        var result = flatRepository.findFirstCreatedWithHouse(houseId, PageRequest.of(0, 1));

        return Optional.ofNullable(result.isEmpty() ? null : result.get(0))
                .map(mapper::toFlat);
    }

    public long findCountByHouseGreaterThan(House house) {
        var dao = houseRepository.findByNameAndYearAndNumberOfFlatsOnFloorAndNumberOfLiftsAndTpcStatus(
                        house.name(),
                        house.year(),
                        house.numberOfFlatsOnFloor(),
                        house.numberOfLifts(),
                        TpcStatus.COMMITED)
                .orElse(houseRepository.save(mapper.toHouseDao(house)));

        return flatRepository.findCountByHouseGreaterThan(dao);
    }

    public List<Flat> findAllByNameStartingWith(String name) {
        return flatRepository.findAllByNameStartingWithAndTpcStatus(name, TpcStatus.COMMITED).stream().map(mapper::toFlat).toList();
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
        houseRepository.findById(flat.getHouse().getId())
                .ifPresent(flat::setHouse);
    }

    private void notLink(FlatDao flat) {
        // Чтобы JPA точно посчитал новыми объекты
        flat.getHouse().setId(null);
    }

}
