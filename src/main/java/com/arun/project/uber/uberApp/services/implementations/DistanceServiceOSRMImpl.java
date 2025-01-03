package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.services.DistanceService;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;


@Service
public class DistanceServiceOSRMImpl implements DistanceService {


    @Override
    public Double calculateDistance(Point src, Point dest) {
        return 0.0;
    }
}
