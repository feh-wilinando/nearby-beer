package com.github.fehwilinando.nearbybeer.end_to_end;

import com.github.fehwilinando.nearbybeer.utils.GraphQLTestUtils;
import com.github.fehwilinando.nearbybeer.utils.queries.CreatePOSQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@Transactional
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CreatePOSTest {

    @Autowired
    private GraphQLTestUtils graphQL;

    @Autowired
    private CreatePOSQuery query;


    @Test
    public void shouldCreateANewPOS(){
        query.setTradingName("Friend's bar");
        query.setOwnerName("Fernando");
        query.setDocument("123.123.123-123");

        graphQL.request(query)
                .successfully()
                    .resultMatchWith("src/test/resources/graphql/output/mutations/create_POS_fernando.json");

    }

    @Test
    public void shouldGetAnErrorWhenTryingToCreateANewPOSWithDuplicatedDocument(){

        query.setTradingName("Friend's bar");
        query.setOwnerName("Fernando");
        query.setDocument("123.123.123-123");


        graphQL.request(query)
                .successfully()
                    .resultMatchWith("src/test/resources/graphql/output/mutations/create_POS_fernando.json");

        graphQL.request(query)
                .withErrors()
                    .resultMatchWith("src/test/resources/graphql/output/mutations/create_POS_with_existing_document.json");

    }

    @Test
    public void shouldGetAnErrorWhenTryingToCreateANewPOSWithEmptyOwnerName(){

        query.setOwnerName("");
        query.setTradingName("Friend's bar");
        query.setDocument("123.123.123-123");

        graphQL.request(query)
                .withErrors()
                    .resultMatchWith("src/test/resources/graphql/output/mutations/create_POS_with_empty_owner_name.json");

    }


}
