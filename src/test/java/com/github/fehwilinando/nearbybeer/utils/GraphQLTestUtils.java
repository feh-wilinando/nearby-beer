package com.github.fehwilinando.nearbybeer.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class GraphQLTestUtils {

    private final TestRestTemplate restTemplate;
    private final String graphQLEndpoint;

    public GraphQLTestUtils(TestRestTemplate restTemplate, @Value("${graphql.servlet.mapping:/graphql}") String graphQLEndpoint) {
        this.restTemplate = restTemplate;
        this.graphQLEndpoint = graphQLEndpoint;
    }


    public ResponseEntity<JsonNode> requestWith(JsonNode input){
        RequestEntity<JsonNode> request = RequestEntity.post(URI.create(graphQLEndpoint))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON)
                                            .body(input);

        return restTemplate.exchange(request, JsonNode.class);
    }
}
