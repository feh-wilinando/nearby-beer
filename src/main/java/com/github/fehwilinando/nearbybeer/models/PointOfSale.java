package com.github.fehwilinando.nearbybeer.models;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity(name = "points_of_sale")
public class PointOfSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "trading_name")
    private String tradingName;

    @NotEmpty
    @Column(name = "owner_name")
    private String ownerName;

    @NotEmpty
    @Column(unique = true)
    private String document;

    @NotNull
    private Point address;

    @NotNull
    @Column(name = "coverage_area")
    private MultiPolygon coverageArea;

    /**
     * @deprecated frameworks only
     */
    @Deprecated
    PointOfSale() { }

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

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointOfSale that = (PointOfSale) o;
        return Objects.equals(document, that.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document);
    }
}
