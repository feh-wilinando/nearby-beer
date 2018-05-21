package com.github.fehwilinando.nearbybeer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fehwilinando.nearbybeer.graphql.exceptions.DocumentAlreadyExistException;
import com.github.fehwilinando.nearbybeer.graphql.exceptions.POSNotFoundException;
import com.github.fehwilinando.nearbybeer.graphql.exceptions.ValidationFailException;
import com.github.fehwilinando.nearbybeer.graphql.inputs.AddressInput;
import com.github.fehwilinando.nearbybeer.graphql.inputs.CoordinateInput;
import com.github.fehwilinando.nearbybeer.graphql.inputs.CoverageAreaInput;
import com.github.fehwilinando.nearbybeer.graphql.inputs.NewPosInput;
import com.github.fehwilinando.nearbybeer.models.PointOfSale;
import com.github.fehwilinando.nearbybeer.utils.JsonReader;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Transactional
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJpa
public class PointsOfSaleServiceTest {


    @Autowired
    private PointsOfSaleService service;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private JsonReader reader;

    private AddressInput address;
    private CoverageAreaInput coverageArea;

    @Before
    public void setup() throws IOException {

        address = reader.readFile("src/test/resources/address.json", AddressInput.class);
        coverageArea = reader.readFile("src/test/resources/coverageArea.json", CoverageAreaInput.class);
    }

    @Test
    public void shouldGetAPointOfSaleWhenTheGivenIdIs1(){
        PointOfSale pos = service.findById(1);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(pos.getId()).isEqualTo(1);
            softAssertions.assertThat(pos.getTradingName()).isEqualTo("Adega Osasco");
            softAssertions.assertThat(pos.getOwnerName()).isEqualTo("Ze da Ambev");
            softAssertions.assertThat(pos.getDocument()).isEqualTo("02.453.716/000170");
        });
    }

    @Test
    public void shouldThrowPOSNotFoundWhenTheGivenIdNotExisting(){

        assertThatExceptionOfType(POSNotFoundException.class)
                .isThrownBy(() -> service.findById(-1))
                    .withMessage("POS with id -1 not found!");
    }


    @Test
    @SuppressWarnings("unchecked")
    public void shouldSaveAPointOfSaleThroughTheNewPosInput(){
        Integer lastSavedId = service.save(createPOSInput("123.123.123-122")).getId();

        PointOfSale pos = service.save(createPOSInput("123.123.123-123"));

        Point expectedAddress = address.toPoint(mapper);
        MultiPolygon expectedCoverageArea = coverageArea.toMultiPolygon(mapper);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(pos.getId()).isEqualTo(lastSavedId + 1);
            softAssertions.assertThat(pos.getTradingName()).isEqualTo("Friend's bar");
            softAssertions.assertThat(pos.getOwnerName()).isEqualTo("Fernando");
            softAssertions.assertThat(pos.getDocument()).isEqualTo("123.123.123-123");
            softAssertions.assertThat(pos.getAddress()).isEqualTo(expectedAddress);
            softAssertions.assertThat(pos.getCoverageArea()).isEqualTo(expectedCoverageArea);
        });

        PointOfSale newPOS = service.findById(lastSavedId + 1);

        assertThat(newPOS).isEqualTo(pos);

    }


    @Test
    public void shouldThrowExceptionWhenTryingToSaveANewPOSWithADuplicatedDocument(){
        NewPosInput first = createPOSInput("123.123.123-123");
        NewPosInput second = createPOSInput("123.123.123-123");

        service.save(first);

        assertThatExceptionOfType(DocumentAlreadyExistException.class)
                    .isThrownBy(() -> service.save(second))
                        .withMessage("Document 123.123.123-123 already exist!");
    }


    @Test
    public void shouldThrowExceptionWhenTryingToSaveANewPOSWithEmptyTradingName(){
        NewPosInput input = createPOSInput("123.123.123-123");

        input.setTradingName("");


        assertThatExceptionOfType(ValidationFailException.class)
                .isThrownBy(() -> service.save(input))
                    .withMessage("Trading name required!");
    }



    @Test
    public void shouldReturnAListOfNearTheVilaMariana(){
        CoordinateInput input = new CoordinateInput();
        input.setLatitude(-23.588830);
        input.setLongitude(-46.631500);

        PointOfSale first = service.findById(41);
        PointOfSale second = service.findById(40);
        PointOfSale third = service.findById(13);
        PointOfSale fourth = service.findById(48);

        List<PointOfSale> allNearTheCoordinate = service.findAllNearTheCoordinate(input);

        assertThat(allNearTheCoordinate)
                                .isNotEmpty()
                                    .containsSequence(first, second, third, fourth);
    }

    @Test
    public void shouldReturnEmptyListOfNearTheOcean(){
        CoordinateInput input = new CoordinateInput();
        input.setLatitude(-24.442630);
        input.setLongitude(-46.194287);

        List<PointOfSale> allNearTheCoordinate = service.findAllNearTheCoordinate(input);

        assertThat(allNearTheCoordinate).isEmpty();
    }

    private NewPosInput createPOSInput(String document) {
        NewPosInput posInput = new NewPosInput();

        posInput.setTradingName("Friend's bar");
        posInput.setOwnerName("Fernando");
        posInput.setDocument(document);
        posInput.setAddress(address);
        posInput.setCoverageArea(coverageArea);
        return posInput;
    }
}