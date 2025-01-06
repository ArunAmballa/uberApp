package com.arun.project.uber.uberApp.strategies;

import com.arun.project.uber.uberApp.strategies.implementations.DriverMatchingHighestRatedDriverStrategy;
import com.arun.project.uber.uberApp.strategies.implementations.DriverMatchingNearestDriverStrategy;
import com.arun.project.uber.uberApp.strategies.implementations.RideFareDefaultFareCalculationStrategy;
import com.arun.project.uber.uberApp.strategies.implementations.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final RideFareDefaultFareCalculationStrategy rideFareDefaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;

    //Deciding DriverMatching Strategy Based on Rating
    public DriverMatchingStrategy getDriverMatchingStrategy(double rating) {
        if(rating>=4.8){
            return driverMatchingHighestRatedDriverStrategy;
        }else{
            return driverMatchingNearestDriverStrategy;
        }
    }

    //Fare Calculation Strategy Based on Time
    public RideFareCalculationStrategy getRideFareStrategy() {
        LocalTime surgeStartTime=LocalTime.of(18,0);
        LocalTime surgeEndTime=LocalTime.of(21,0);
        LocalTime currentTime=LocalTime.now();
        if(currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime)){
            return rideFareSurgePricingFareCalculationStrategy;
        }
        else{
            return rideFareDefaultFareCalculationStrategy;
        }
    }

}
