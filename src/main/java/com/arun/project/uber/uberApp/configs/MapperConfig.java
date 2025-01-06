package com.arun.project.uber.uberApp.configs;

import com.arun.project.uber.uberApp.dto.PointDto;
import com.arun.project.uber.uberApp.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getMapper(){
        ModelMapper mapper=new ModelMapper();

        //Converting from PointDto to Point
        mapper.typeMap(PointDto.class, Point.class).setConverter(context -> {
            PointDto pointDto=context.getSource();
            return GeometryUtil.createPoint(pointDto);
        });

        //Converting from Point to PointDto
        mapper.typeMap(Point.class,PointDto.class).setConverter(context-> {
            Point point=context.getSource();
            double coordinates[]={
                    point.getX(),
                    point.getY()
            };
            return new PointDto(coordinates);
        });

        return mapper;
    }


//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new PasswordEncoder();
//    }
//


}
