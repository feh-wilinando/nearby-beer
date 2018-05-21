package com.github.fehwilinando.nearbybeer.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GraphQLResponseBodyMatchBuilder {

    private final JsonNode response;
    private final JsonReader reader;

    GraphQLResponseBodyMatchBuilder(ResponseEntity<JsonNode> response, JsonReader reader) {
        this.response = response.getBody();
        this.reader = reader;
    }

    public void resultMatchWith(String fullQualifiedFileName){

        JsonNode expected = reader.readFile(fullQualifiedFileName);

        assertThat(response).isEqualTo(expected);
    }
}
