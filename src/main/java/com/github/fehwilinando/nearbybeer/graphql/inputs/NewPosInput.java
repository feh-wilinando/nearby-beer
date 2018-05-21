package com.github.fehwilinando.nearbybeer.graphql.inputs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fehwilinando.nearbybeer.graphql.exceptions.ValidationFailException;
import com.github.fehwilinando.nearbybeer.models.PointOfSale;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

public class NewPosInput {
    private String tradingName;
    private String ownerName;
    private String document;
    private AddressInput address;
    private CoverageAreaInput coverageArea;


    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public AddressInput getAddress() {
        return address;
    }

    public void setAddress(AddressInput address) {
        this.address = address;
    }

    public CoverageAreaInput getCoverageArea() {
        return coverageArea;
    }

    public void setCoverageArea(CoverageAreaInput coverageArea) {
        this.coverageArea = coverageArea;
    }

    public PointOfSale toPointOfSale(ObjectMapper mapper){
        try {
            Point pointAddress = address.toPoint(mapper);
            MultiPolygon polygonCoverageArea = coverageArea.toMultiPolygon(mapper);

            return new PointOfSale(tradingName, ownerName, document, pointAddress, polygonCoverageArea);
        }catch (IllegalArgumentException e){
            throw new ValidationFailException(e.getMessage());
        }
    }

}
