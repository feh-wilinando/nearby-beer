package com.github.fehwilinando.nearbybeer.utils.queries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fehwilinando.nearbybeer.utils.JsonReader;
import org.springframework.stereotype.Component;

@Component
public class NearbyBeerQuery implements Query{

    private final JsonNode query;

    NearbyBeerQuery(JsonReader reader) {
        query = reader.readFile("src/test/resources/graphql/input/queries/nearby_beer.json");
    }

    @Override
    public JsonNode getJson() {
        return query;
    }

    public void setLatitude(Double latitude){
        ((ObjectNode) query.get("variables")).put("lat", latitude);
    }

    public void setLongitude(Double longitude){
        ((ObjectNode) query.get("variables")).put("lng", longitude);
    }

}
