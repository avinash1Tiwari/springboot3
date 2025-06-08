package com.avinash.project.uber.uberApp.utils;

import com.avinash.project.uber.uberApp.dto.PointDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtil {


    public static Point createPoint(PointDto pointDto) {
        double[] coordinates = pointDto.getCoordinates();
        if (coordinates == null || coordinates.length != 2) {
            throw new IllegalArgumentException("Invalid coordinates for Point creation.");
        }

        // Create the point and set SRID to 4326
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Point point = geometryFactory.createPoint(new Coordinate(coordinates[0], coordinates[1]));
        point.setSRID(4326);
        return point;
//        }
    }



}
