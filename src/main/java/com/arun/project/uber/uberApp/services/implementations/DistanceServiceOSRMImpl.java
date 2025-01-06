package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API_BASE_URL="https://router.project-osrm.org/route/v1/driving/";

    @Override
    public Double calculateDistance(Point src, Point dst) {

        try{
            OSRMResponseDto responseDto= RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(src.getX() + "," + src.getY() + ";" + dst.getX() + "," + dst.getY())
                    .retrieve()
                    .body(OSRMResponseDto.class);

            return responseDto.getRoutes().get(0).getDistance() /1000.0;

        }catch(Exception e){
            throw new RuntimeException("Error Getting Data from OSRM"+e.getMessage());
        }
    }
}

@Data
class OSRMResponseDto{

    private List<OSRMRoute> routes;

}

@Data
class OSRMRoute{
    private Double distance;
}
