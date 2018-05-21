package com.github.fehwilinando.nearbybeer.repositories;

import com.github.fehwilinando.nearbybeer.models.PointOfSale;
import com.vividsolutions.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PointsOfSale extends Repository<PointOfSale, Integer> {
    Optional<PointOfSale> findById(Integer id);

    void save(PointOfSale pointOfSale);

    @Query(value = "select p from #{#entityName} p where st_contains(p.coverageArea, :point) = true order by st_distance(p.address, :point)")
    List<PointOfSale> findAllNearThePoint(@Param("point") Point point);
}
