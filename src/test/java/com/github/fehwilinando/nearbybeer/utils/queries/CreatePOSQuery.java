package com.github.fehwilinando.nearbybeer.utils.queries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fehwilinando.nearbybeer.utils.JsonReader;
import org.springframework.stereotype.Component;

@Component
public class CreatePOSQuery implements Query{

    private final JsonNode query;

    CreatePOSQuery(JsonReader reader) {
        query = reader.readFile("src/test/resources/graphql/input/mutations/createPOS.json");
    }

    public void setTradingName(String tradingName){
        ((ObjectNode) query.get("variables").get("pos")).put("tradingName", tradingName);
    }

    public void setOwnerName(String ownerName){
        ((ObjectNode) query.get("variables").get("pos")).put("ownerName", ownerName);
    }

    public void setDocument(String document){
        ((ObjectNode) query.get("variables").get("pos")).put("document", document);
    }

    @Override
    public JsonNode getJson() {
        return query;
    }
}
