package com.github.fehwilinando.nearbybeer.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class POSQuery {

    private final JsonNode query;


    POSQuery(JsonReader reader) throws IOException {
        query = reader.readFile("src/test/resources/graphql/input/queries/pos.json");
    }


    public void setId(Long id){
        ((ObjectNode) query.get("variables")).put("id", id);
    }

    public JsonNode getJson(){
        return query;
    }
}
