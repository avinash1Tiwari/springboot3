//package com.avinash.project.uber.uberApp.utils;
//
//
//import com.avinash.project.uber.uberApp.dto.PointDto;
//import com.avinash.project.uber.uberApp.dto.RideDto;
//import com.avinash.project.uber.uberApp.entities.Ride;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.locationtech.jts.geom.Point;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Component
//public class DriverUtils {
//
//    ModelMapper modelMapper = new ModelMapper();
//    public RideDto converRideTorideDto(Ride savedRide)
//    {
//        Point pickup = savedRide.getPickupLocation();
//        Point dropOff = savedRide.getDropOffLocation();
//        savedRide.setPickupLocation(null);
//        savedRide.setDropOffLocation(null);
//
//
//        RideDto rideDto = modelMapper.map(savedRide, RideDto.class);
//        PointDto pickupDto = new PointDto("Point", new double[]{pickup.getX(), pickup.getY()});
//        rideDto.setPickupLocation(pickupDto);
//        PointDto dropOffDto = new PointDto("Point", new double[]{dropOff.getX(), dropOff.getY()});
//        rideDto.setDropOffLocation(dropOffDto);
//        savedRide.setPickupLocation(pickup);
//        savedRide.setDropOffLocation(dropOff);
//        return rideDto;
//    }
//}














package com.avinash.project.uber.uberApp.utils;

import com.avinash.project.uber.uberApp.dto.PointDto;
import com.avinash.project.uber.uberApp.dto.RideDto;
import com.avinash.project.uber.uberApp.entities.Ride;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverUtils {

    private final ModelMapper modelMapper;

    public RideDto converRideTorideDto(Ride savedRide) {
        Point pickup = savedRide.getPickupLocation();
        Point dropOff = savedRide.getDropOffLocation();
        savedRide.setPickupLocation(null);
        savedRide.setDropOffLocation(null);

        RideDto rideDto = modelMapper.map(savedRide, RideDto.class);

        PointDto pickupDto = new PointDto("Point", new double[]{pickup.getX(), pickup.getY()});
        rideDto.setPickupLocation(pickupDto);

        PointDto dropOffDto = new PointDto("Point", new double[]{dropOff.getX(), dropOff.getY()});
        rideDto.setDropOffLocation(dropOffDto);

        savedRide.setPickupLocation(pickup);
        savedRide.setDropOffLocation(dropOff);

        return rideDto;
    }
}
