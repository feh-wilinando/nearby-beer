package com.github.fehwilinando.nearbybeer.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GraphQLResponseBuilder {

    private final ResponseEntity<JsonNode> response;
    private final boolean fildErrorIsMissing;
    private final JsonReader jsonReader;

    GraphQLResponseBuilder(ResponseEntity<JsonNode> response, JsonReader jsonReader) {
        this.jsonReader = jsonReader;
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        this.fildErrorIsMissing = response.getBody().path("errors").isMissingNode();
        this.response = response;

    }

    public GraphQLResponseBodyMatchBuilder withErrors(){
        assertThat(fildErrorIsMissing)
                .as("response has no field 'errors'")
                    .describedAs(response.getBody().toString())
                        .isFalse();

        return new GraphQLResponseBodyMatchBuilder(response, jsonReader);
    }

    public GraphQLResponseBodyMatchBuilder successfully() {
        assertThat(fildErrorIsMissing)
                .as("response has field 'errors'")
                    .describedAs(response.getBody().toString())
                        .isTrue();

        return new GraphQLResponseBodyMatchBuilder(response, jsonReader);
    }

}
