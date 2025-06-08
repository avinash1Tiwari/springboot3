package com.avinash.project.uber.uberApp.configs;


import com.avinash.project.uber.uberApp.dto.PointDto;
import com.avinash.project.uber.uberApp.dto.RiderDto;
import com.avinash.project.uber.uberApp.entities.Rider;
import com.avinash.project.uber.uberApp.utils.GeometryUtil;

import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class MapperConfig {
//
//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper mapper = new ModelMapper();
//
//        mapper.typeMap(PointDto.class, Point.class).setConverter(context -> {
//            PointDto pointDto = context.getSource();
//            return GeometryUtil.createPoint(pointDto);
//        });
//
//        mapper.typeMap(Point.class, PointDto.class).setConverter(context -> {
//            Point point = context.getSource();
//            double coordinates[] = {
//                    point.getX(),
//                    point.getY()
//            };
//            return new PointDto(coordinates);
//        });
//
//
//        return mapper;
//    }
//}
//
//
//
//
//
//


















@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // PointDto → Point
        mapper.typeMap(PointDto.class, Point.class).setConverter(context -> {
            PointDto pointDto = context.getSource();
            return GeometryUtil.createPoint(pointDto);
        });

        // Point → PointDto
        mapper.typeMap(Point.class, PointDto.class).setConverter(context -> {
            Point point = context.getSource();
            double coordinates[] = {
                    point.getX(),
                    point.getY()
            };
            return new PointDto(coordinates);
        });

        // Rider → RiderDto
        mapper.typeMap(Rider.class, RiderDto.class).addMappings(m -> {
            m.map(Rider::getUser, RiderDto::setUserDto);
        });

        return mapper;
    }
}
