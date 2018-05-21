package com.github.fehwilinando.nearbybeer.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.fehwilinando.nearbybeer.graphql.inputs.CoordinateInput;
import com.github.fehwilinando.nearbybeer.models.PointOfSale;
import com.github.fehwilinando.nearbybeer.services.PointsOfSaleService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {


    private final PointsOfSaleService service;

    public Query(PointsOfSaleService service) {
        this.service = service;
    }


    public PointOfSale pos(Integer id){
        return service.findById(id);
    }

    public List<PointOfSale> nearby_beer(CoordinateInput coordinate){
        return service.findAllNearTheCoordinate(coordinate);
    }
}
