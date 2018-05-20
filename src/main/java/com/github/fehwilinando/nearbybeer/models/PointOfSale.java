package com.github.fehwilinando.nearbybeer.models;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import org.springframework.util.Assert;

public class PointOfSale {
    private String tradingName;
    private String ownerName;
    private String document;
    private Point address;
    private MultiPolygon coverageArea;


    public PointOfSale(String tradingName, String ownerName, String document, Point address, MultiPolygon coverageArea) {

        Assert.hasText(tradingName, "Trading name required!");
        Assert.hasText(ownerName, "Owner name required!");
        Assert.hasText(document, "Document required!");

        Assert.notNull(address, "Address required!");
        Assert.isTrue(address.isValid(), "Invalid address coordinates!");

        Assert.notNull(coverageArea, "Coverage area required!");
        Assert.isTrue(coverageArea.isValid(), "Invalid coverage area multi polygon!");

        this.tradingName = tradingName;
        this.ownerName = ownerName;
        this.document = document;
        this.address = address;
        this.coverageArea = coverageArea;
    }

    public String getTradingName() {
        return tradingName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getDocument() {
        return document;
    }

    public Point getAddress() {
        return (Point) address.clone();
    }

    public MultiPolygon getCoverageArea() {
        return (MultiPolygon) coverageArea.clone();
    }
}
