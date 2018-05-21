package com.github.fehwilinando.nearbybeer.graphql.inputs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.MultiPolygon;

public class CoverageAreaInput {

    private String type;
    private double[][][][] coordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double[][][][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[][][][] coordinates) {
        this.coordinates = coordinates;
    }

    public MultiPolygon toMultiPolygon(ObjectMapper mapper) {
        return mapper.convertValue(this, MultiPolygon.class);
    }
}
