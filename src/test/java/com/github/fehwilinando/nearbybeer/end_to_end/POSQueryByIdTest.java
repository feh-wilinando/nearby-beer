package com.github.fehwilinando.nearbybeer.end_to_end;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fehwilinando.nearbybeer.utils.GraphQLTestUtils;
import com.github.fehwilinando.nearbybeer.utils.JsonReader;
import com.github.fehwilinando.nearbybeer.utils.POSQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class POSQueryByIdTest {


    @Autowired
    private GraphQLTestUtils graphQL;

    @Autowired
    private JsonReader reader;

    @Autowired
    private POSQuery query;


    @Test
    public void shouldGetAPointOfSaleWithExistingId() {

        JsonNode jsonQuery = query.setId(1L);

        ResponseEntity<JsonNode> response = graphQL.requestWith(jsonQuery);

        assertThatResponseWithNoErrors(response);

        FileSystemResource expectedFile = new FileSystemResource("src/test/resources/graphql/output/queries/pos_of_id_1.json");

        JsonNode expectedResult = reader.readResource(expectedFile);

        assertThat(response.getBody()).isEqualTo(expectedResult);

    }



    @Test
    public void shouldGetAnErrorWithNotExistingId() {

        JsonNode jsonQuery = query.setId(52L);

        ResponseEntity<JsonNode> response = graphQL.requestWith(jsonQuery);

        assertThatResponseWithErrors(response);

        FileSystemResource expectedFile = new FileSystemResource("src/test/resources/graphql/output/queries/pos_of_not_existing_id.json");

        JsonNode expectedResult = reader.readResource(expectedFile);

        assertThat(response.getBody()).isEqualTo(expectedResult);

    }


    private void assertThatResponseWithNoErrors(ResponseEntity<JsonNode> response){

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        boolean hasNoFieldErrors = response.getBody().path("errors").isMissingNode();

        assertThat(hasNoFieldErrors)
                .as("response has field 'errors'")
                    .isTrue();
    }

    private void assertThatResponseWithErrors(ResponseEntity<JsonNode> response){

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        boolean hasNoFieldErrors = response.getBody().path("errors").isMissingNode();

        assertThat(hasNoFieldErrors)
                .as("response has no field 'errors'")
                    .isFalse();
    }

}
