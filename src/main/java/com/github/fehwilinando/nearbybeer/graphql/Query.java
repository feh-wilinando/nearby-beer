package com.github.fehwilinando.nearbybeer.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.fehwilinando.nearbybeer.graphql.exceptions.POSNotFoundException;
import com.github.fehwilinando.nearbybeer.models.PointOfSale;
import com.github.fehwilinando.nearbybeer.repositories.PointsOfSale;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class Query implements GraphQLQueryResolver {

    private final PointsOfSale pointsOfSale;

    public Query(PointsOfSale pointsOfSale) {
        this.pointsOfSale = pointsOfSale;
    }


    public PointOfSale pos(Integer id){
        Supplier<POSNotFoundException> notFound = () -> new POSNotFoundException("POS with id " + id + " not found!", id);

        return pointsOfSale.findById(id).orElseThrow(notFound);
    }

}
