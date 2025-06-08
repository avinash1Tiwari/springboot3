package com.avinash.project.uber.uberApp.services.Implementation;

import com.avinash.project.uber.uberApp.advices.ApiResponse;
import com.avinash.project.uber.uberApp.dto.*;
import com.avinash.project.uber.uberApp.entities.*;
import com.avinash.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.avinash.project.uber.uberApp.entities.enums.RideStatus;
import com.avinash.project.uber.uberApp.repositories.RideRequestRepository;
import com.avinash.project.uber.uberApp.repositories.RiderRepository;
import com.avinash.project.uber.uberApp.services.DriverService;
import com.avinash.project.uber.uberApp.services.RatingService;
import com.avinash.project.uber.uberApp.services.RideService;
import com.avinash.project.uber.uberApp.services.RiderService;
import com.avinash.project.uber.uberApp.strategies.RideStrategyManager;
import com.avinash.project.uber.uberApp.utils.DriverUtils;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Data
@Getter
@RequiredArgsConstructor
@Service
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    /// here in all cases we have used "final" , to make constructor basede depandency and not used "@AutoWired"=> b/c now with final no one can change it's value i.e, full immutability
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideStrategyManager rideStrategyManager;
    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    private final RideService rideService;
    private final DriverService driverService;
   private final DriverUtils driverUtils;
   private final RatingService ratingService;



    @Override
    @Transactional
    public ResponseEntity<ApiResponse<RideRequestDto>> requestRide(RideRequestDto dto) {
        try {
            RideRequest rideRequest = modelMapper.map(dto, RideRequest.class);

            // Handle custom fields manually
            rideRequest.setPickupLocation(modelMapper.map(dto.getPickupLocation(), Point.class));
            rideRequest.setDropOffLocation(modelMapper.map(dto.getDropOffLocation(), Point.class));

            Rider rider = getCurrentRider();
            rideRequest.setRider(rider);
            rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
            Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
            rideRequest.setFare(fare);

            RideRequest saved = rideRequestRepository.save(rideRequest);
            RideRequest fullResponse = rideRequestRepository.findById(saved.getId()).orElseThrow();
            RideRequestDto responsedto = modelMapper.map(fullResponse, RideRequestDto.class);
            RiderDto riderDto = modelMapper.map(fullResponse.getRider(), RiderDto.class);

            responsedto.setRider(riderDto);


            ApiResponse<RideRequestDto> res = new ApiResponse<>(responsedto);

            return ResponseEntity.ok(res);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


    @Override
    public RideDto cancelRide(Long rideId) {

//
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider()))
        {
            throw new RuntimeException("Rider does not own this ride with rideId : " + rideId);
        }


//        cancel the ride
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);

//        once driver cancels => he/she is available again

       driverService.updateDriverAvailability(ride.getDriver(),true);
       return driverUtils.converRideTorideDto(savedRide);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {

        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

//        2 to check if same rider is owning the ride
        if (!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider is not owner of the ride");
        }

//        3.rate only if ride is confirmed

        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride status is not ended , hence can not start rating , status : " + ride.getRideStatus());
        }

//  4.   rate the driver
       Drivers driver = ratingService.rateDriver(ride,rating);
        return modelMapper.map(driver,DriverDto.class);
    }

    @Override
    public RiderDto getMyProfile() {

        Rider rider = getCurrentRider();
        return modelMapper.map(rider,RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {

        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider,pageRequest).map(
                driverUtils::converRideTorideDto
        );
    }

    @Override
    public Rider createNewRider(User user) {

        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();

        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
//use spring security to find the current-driver
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user).orElseThrow(() -> new ResourceAccessException(
                "Rider not associated with user having id : " + user.getId()
        ));
    }


    private Point convertToPoint(PointDto pointDto) {
        double[] coords = pointDto.getCoordinates();
        Point point = geometryFactory.createPoint(new Coordinate(coords[0], coords[1]));
        point.setSRID(4326);
        return point;
    }

}
