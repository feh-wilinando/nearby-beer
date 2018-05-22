package com.github.fehwilinando.nearbybeer.end_to_end;

import com.github.fehwilinando.nearbybeer.utils.GraphQLTestUtils;
import com.github.fehwilinando.nearbybeer.utils.queries.POSQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class POSQueryByIdTest {


    @Autowired
    private GraphQLTestUtils graphQL;

    @Autowired
    private POSQuery query;


    @Test
    public void shouldGetAPointOfSaleWithExistingId() {

        query.setId(1L);

        graphQL
                .request(query)
                    .successfully()
                        .resultMatchWith("src/test/resources/graphql/output/queries/pos_of_id_1.json");

    }



    @Test
    public void shouldGetAnErrorWithNotExistingId() {

        query.setId(52L);

        graphQL
                .request(query)
                    .withErrors()
                        .resultMatchWith("src/test/resources/graphql/output/queries/pos_of_not_existing_id.json");
    }


    @Test
    public void shouldGetAnErrorWhenIdIsNull(){

        query.setId(null);


        graphQL
                .request(query)
                    .withErrors()
                        .resultMatchWith("src/test/resources/graphql/output/queries/pos_null_id.json");

    }

}
