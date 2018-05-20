package com.github.fehwilinando.nearbybeer.models;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


public class PointOfSaleTest {

    private String tradingName;
    private String ownerName;
    private String document;
    private Point address;
    private MultiPolygon coverageArea;
    private WKTReader wktReader = new WKTReader();


    @Before
    public void setup() throws ParseException {
        tradingName = "friend's bar";
        ownerName = "Fernando";
        document = "123.123.123-12";


        address = (Point) wktReader.read("POINT(-46.631500  -23.588830)");
        coverageArea  = (MultiPolygon) wktReader.read("MULTIPOLYGON (((-43.36556 -22.99669, -43.36539 -23.01928, -43.26583 -23.01802, -43.25724 -23.00649, -43.23355 -23.00127, -43.2381 -22.99716, -43.23866 -22.99649, -43.24063 -22.99756, -43.24634 -22.99736, -43.24677 -22.99606, -43.24067 -22.99381, -43.24886 -22.99121, -43.25617 -22.99456, -43.25625 -22.99203, -43.25346 -22.99065, -43.29599 -22.98283, -43.3262 -22.96481, -43.33427 -22.96402, -43.33616 -22.96829, -43.342 -22.98157, -43.34817 -22.97967, -43.35142 -22.98062, -43.3573 -22.98084, -43.36522 -22.98032, -43.36696 -22.98422, -43.36717 -22.98855, -43.36636 -22.99351, -43.36556 -22.99669)))");
    }

    @Test
    public void cantInstantiateWithANullTradingName(){

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale(null, ownerName, document, address, coverageArea))
                    .withMessage("Trading name required!");


    }

    @Test
    public void cantInstantiateWithAnEmptyTradingName(){

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale("", ownerName, document, address, coverageArea))
                .withMessage("Trading name required!");

    }

    @Test
    public void cantInstantiateWithANullOwnerName(){
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale(tradingName, null, document, address, coverageArea))
                .withMessage("Owner name required!");

    }

    @Test
    public void cantInstantiateWithAnEmptyOwnerName(){
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale(tradingName, "", document, address, coverageArea))
                .withMessage("Owner name required!");

    }

    @Test
    public void cantInstantiateWithANullDocument() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale(tradingName, ownerName, null, address, coverageArea))
                .withMessage("Document required!");

    }

    @Test
    public void cantInstantiateWithAnEmptyDocument() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale(tradingName, ownerName, null, address, coverageArea))
                .withMessage("Document required!");

    }


    @Test
    public void cantInstantiateWithANullAddress(){
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale(tradingName, ownerName, document, null, coverageArea))
                .withMessage("Address required!");

    }


    @Test
    public void cantInstantiateWithAnInvalidGeometricPointAddress() throws ParseException {
        Point invalidAddress = (Point) wktReader.read("POINT(NAN 10)");


        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale(tradingName, ownerName, document, invalidAddress, coverageArea))
                .withMessage("Invalid address coordinates!");

    }

    @Test
    public void cantInstantiateWithANullCoverageArea(){
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale(tradingName, ownerName, document, address, null))
                .withMessage("Coverage area required!");

    }


    @Test
    public void cantInstantiateWithAInvalidGeometricMultipolygonCoverageArea() throws ParseException {
        MultiPolygon invalidCoverageArea = (MultiPolygon) wktReader.read("MULTIPOLYGON (((-67.83039 -9.95782, NAN -9.98487, -67.78627 -9.98825, -67.78885 NAN, -67.83039 -9.95782)))");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PointOfSale(tradingName, ownerName, document, address, invalidCoverageArea))
                .withMessage("Invalid coverage area multi polygon!");

    }



    @Test
    @SuppressWarnings("unchecked")
    public void shouldInstantiateAPointOfSaleWithValidArgs(){
        PointOfSale pos = new PointOfSale(tradingName, ownerName, document, address, coverageArea);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(pos.getTradingName()).isEqualTo(tradingName);
            softAssertions.assertThat(pos.getOwnerName()).isEqualTo(ownerName);
            softAssertions.assertThat(pos.getDocument()).isEqualTo(document);
            softAssertions.assertThat(pos.getAddress()).isEqualTo(address);
            softAssertions.assertThat(pos.getCoverageArea()).isEqualTo(coverageArea);
        });
    }

}