package com.github.fehwilinando.nearbybeer.graphql.inputs;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class CoordinateInput {
    private Double latitude;
    private Double longitude;


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public Point toPoint(GeometryFactory factory){
        Coordinate coordinate = new Coordinate(longitude, latitude);
        return factory.createPoint(coordinate);
    }

}
