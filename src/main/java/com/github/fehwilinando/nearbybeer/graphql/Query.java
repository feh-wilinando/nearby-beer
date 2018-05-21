package com.github.fehwilinando.nearbybeer.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.github.fehwilinando.nearbybeer.graphql.exceptions.POSNotFoundException;
import com.github.fehwilinando.nearbybeer.graphql.inputs.CoordinateInput;
import com.github.fehwilinando.nearbybeer.models.PointOfSale;
import com.github.fehwilinando.nearbybeer.repositories.PointsOfSale;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class Query implements GraphQLQueryResolver {

    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final PointsOfSale pointsOfSale;

    public Query(PointsOfSale pointsOfSale) {
        this.pointsOfSale = pointsOfSale;
    }


    public PointOfSale pos(Integer id){
        Supplier<POSNotFoundException> notFound = () -> new POSNotFoundException("POS with id " + id + " not found!", id);

        return pointsOfSale.findById(id).orElseThrow(notFound);
    }

    public List<PointOfSale> nearby_beer(CoordinateInput coordinate){
        Point point = coordinate.toPoint(geometryFactory);

        return pointsOfSale.findAllNearThePoint(point);
    }
}
