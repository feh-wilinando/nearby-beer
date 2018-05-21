package com.github.fehwilinando.nearbybeer.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.github.fehwilinando.nearbybeer.graphql.inputs.NewPosInput;
import com.github.fehwilinando.nearbybeer.models.PointOfSale;
import com.github.fehwilinando.nearbybeer.services.PointsOfSaleService;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {


    private final PointsOfSaleService service;

    public Mutation(PointsOfSaleService service) {
        this.service = service;
    }

    public PointOfSale createPOS(NewPosInput newPos) {
        return service.save(newPos);
    }
}
