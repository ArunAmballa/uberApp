package com.arun.project.uber.uberApp.services;

import org.locationtech.jts.geom.Point;


public interface DistanceService{

    Double calculateDistance(Point src, Point dest);
}
