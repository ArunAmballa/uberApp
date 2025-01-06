package com.arun.project.uber.uberApp.utils;

import com.arun.project.uber.uberApp.dto.PointDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

//Geometry Util is a Class Responsible for converting PointDto to Point
//while sending from API we send PointDto but we want a Point
public class GeometryUtil {

    public static Point createPoint(PointDto pointDto){
        GeometryFactory geometryfactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate=new Coordinate(pointDto.getCoordinates()[0],pointDto.getCoordinates()[1]);
        return geometryfactory.createPoint(coordinate);
    }
}
