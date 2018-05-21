package com.github.fehwilinando.nearbybeer.repositories;

import com.github.fehwilinando.nearbybeer.models.PointOfSale;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PointsOfSale extends Repository<PointOfSale, Integer> {
    Optional<PointOfSale> findById(Integer id);

    void save(PointOfSale pointOfSale);
}
