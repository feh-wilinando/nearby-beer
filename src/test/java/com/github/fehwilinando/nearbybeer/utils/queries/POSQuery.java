package com.github.fehwilinando.nearbybeer.utils.queries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fehwilinando.nearbybeer.utils.JsonReader;
import org.springframework.stereotype.Component;

@Component
public class POSQuery implements Query{

    private final JsonNode query;


    POSQuery(JsonReader reader) {
        query = reader.readFile("src/test/resources/graphql/input/queries/pos.json");
    }


    public void setId(Long id){
        ((ObjectNode) query.get("variables")).put("id", id);
    }

    @Override
    public JsonNode getJson(){
        return query;
    }
}
