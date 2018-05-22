package com.github.fehwilinando.nearbybeer.end_to_end;

import com.github.fehwilinando.nearbybeer.utils.GraphQLTestUtils;
import com.github.fehwilinando.nearbybeer.utils.queries.NearbyBeerQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NearByBeerTest {

    @Autowired
    private GraphQLTestUtils graphQL;

    @Autowired
    private NearbyBeerQuery query;


    @Test
    public void shouldGetFourPOSNearTheVilaMariana(){

        query.setLatitude(-23.588830);
        query.setLongitude(-46.631500);

        String fileResult = "src/test/resources/graphql/output/queries/nearby_beer_vila_mariana.json";

        graphQL
            .request(query)
                    .successfully()
                        .resultMatchWith(fileResult);
    }

    @Test
    public void shouldGetEmptyResultWhenNoPOSNear(){

        query.setLatitude(-24.442630);
        query.setLongitude(-46.194287);

        String fileResult = "src/test/resources/graphql/output/queries/nearby_beer_empty.json";

        graphQL
                .request(query)
                    .successfully()
                        .resultMatchWith(fileResult);
    }



    @Test
    public void shouldGetAnErrorWhenBothLatitudeAndLongitudeIsNull(){

        query.setLatitude(null);
        query.setLongitude(null);

        String fileResult = "src/test/resources/graphql/output/queries/nearby_beer_null_latitude.json";

        graphQL
                .request(query)
                    .withErrors()
                        .resultMatchWith(fileResult);
    }

    @Test
    public void shouldGetAnErrorWhenLatitudeIsNull(){

        query.setLatitude(null);
        query.setLongitude(-46.194287);

        String fileResult = "src/test/resources/graphql/output/queries/nearby_beer_null_latitude.json";

        graphQL
                .request(query)
                    .withErrors()
                        .resultMatchWith(fileResult);
    }


    @Test
    public void shouldGetAnErrorWhenLongitudeIsNull(){

        query.setLatitude(-24.442630);
        query.setLongitude(null);

        String fileResult = "src/test/resources/graphql/output/queries/nearby_beer_null_longitude.json";

        graphQL
                .request(query)
                    .withErrors()
                        .resultMatchWith(fileResult);
    }

}
