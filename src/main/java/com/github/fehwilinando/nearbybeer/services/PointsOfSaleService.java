package com.github.fehwilinando.nearbybeer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fehwilinando.nearbybeer.graphql.exceptions.DocumentAlreadyExistException;
import com.github.fehwilinando.nearbybeer.graphql.exceptions.POSNotFoundException;
import com.github.fehwilinando.nearbybeer.graphql.inputs.CoordinateInput;
import com.github.fehwilinando.nearbybeer.graphql.inputs.NewPosInput;
import com.github.fehwilinando.nearbybeer.models.PointOfSale;
import com.github.fehwilinando.nearbybeer.repositories.PointsOfSale;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsOfSaleService {

    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final ObjectMapper mapper;
    private final PointsOfSale repository;

    public PointsOfSaleService(ObjectMapper mapper, PointsOfSale repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public PointOfSale save(NewPosInput form){
        PointOfSale pointOfSale = form.toPointOfSale(mapper);

        try {
            repository.save(pointOfSale);
        }catch (DataIntegrityViolationException e) {
            throw new DocumentAlreadyExistException("Document " + pointOfSale.getDocument() + " already exist!");
        }

        return pointOfSale;

    }


    public PointOfSale findById(Integer id){
        return repository
                    .findById(id)
                        .orElseThrow(() -> new POSNotFoundException("POS with id " + id + " not found!", id));
    }


    public List<PointOfSale> findAllNearTheCoordinate(CoordinateInput coordinate){
        Point point = coordinate.toPoint(geometryFactory);

        return repository.findAllNearThePoint(point);
    }

}
