package com.avinash.project.uber.uberApp.services.Implementation;

import com.avinash.project.uber.uberApp.dto.DriverDto;
import com.avinash.project.uber.uberApp.dto.PointDto;
import com.avinash.project.uber.uberApp.dto.RideDto;
import com.avinash.project.uber.uberApp.dto.RiderDto;
import com.avinash.project.uber.uberApp.entities.*;
import com.avinash.project.uber.uberApp.entities.enums.RideRequestStatus;
import com.avinash.project.uber.uberApp.entities.enums.RideStatus;
import com.avinash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.avinash.project.uber.uberApp.repositories.DriverRepository;
import com.avinash.project.uber.uberApp.repositories.RideRepository;
import com.avinash.project.uber.uberApp.services.*;
import com.avinash.project.uber.uberApp.utils.DriverUtils;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;


@Data
@Getter
@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final RideRepository rideRepository;
    private final RatingService ratingService;

    ModelMapper modelMapper = new ModelMapper();
    private final DriverUtils driverUtils;
private final PaymentService paymentService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideReqId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideReqId);

        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException("RideRequest cannot be accepted, status is " + rideRequest.getRideRequestStatus());
        }

        Drivers currentDriver = getCurrentDriver();
        if (!currentDriver.getIs_available()) {
            throw new RuntimeException("Driver cannot accept ride due to unavailability");
        }

        currentDriver.setIs_available(false);
        Drivers savedDriver = driverRepository.save(currentDriver);

        Ride ride = rideService.createNewRide(rideRequest, savedDriver);
        System.out.println(ride.toString());

//        return modelMapper.map(ride, RideDto.class);


//        -----------------------------------
        RideDto rideDto = new RideDto();
        rideDto.setId(ride.getId());

// Map Point to PointDto for pickup and drop-off
        Point pickup = ride.getPickupLocation();
        PointDto pickupDto = new PointDto("Point", new double[]{pickup.getX(), pickup.getY()});
        rideDto.setPickupLocation(pickupDto);

// Create PointDto for drop-off location
        Point dropOff = ride.getDropOffLocation();
        PointDto dropOffDto = new PointDto("Point", new double[]{dropOff.getX(), dropOff.getY()});
        rideDto.setDropOffLocation(dropOffDto);

        rideDto.setPickupLocation(pickupDto);
        rideDto.setDropOffLocation(dropOffDto);

// Set simple fields
        rideDto.setDriver(modelMapper.map(ride.getDriver(), DriverDto.class));
        rideDto.setRider(modelMapper.map(ride.getRider(), RiderDto.class));
        rideDto.setCreatedTime(ride.getCreatedTime());
        rideDto.setPaymentMethod(ride.getPaymentMethod());
        rideDto.setRideStatus(ride.getRideStatus());
        rideDto.setOtp(ride.getOtp());
        rideDto.setFare(ride.getFare());
        rideDto.setStartedAt(ride.getStartedAt());
        rideDto.setEndedAt(ride.getEndedAt());
//        -----------------------------------
        return rideDto;
    }






    @Override
    public RideDto cancelRide(Long rideId) {
//       1. get ride
        Ride ride = rideService.getRideById(rideId);

//      2 to check if same driver is starting
        Drivers driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver can not cancel the ride as he has not accepted it earlier");
//            TODO : change it unauthorised-exception
        }

//        ride is cancelled only if it is CONFIRMED
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED))
        {
            throw new RuntimeException("ride can not be cancelled, invalid status: " + ride.getRideStatus());
        }

//        cancel the ride
        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.CANCELLED);

//        once driver cancels => he/she is available again
        driver.setIs_available(true);
        driverRepository.save(driver);

        Point pickup = savedRide.getPickupLocation();
        Point dropOff = savedRide.getDropOffLocation();
        savedRide.setPickupLocation(null);
        savedRide.setDropOffLocation(null);
        RideDto rideDto = modelMapper.map(savedRide, RideDto.class);
        PointDto pickupDto = new PointDto("Point", new double[]{pickup.getX(), pickup.getY()});
        rideDto.setPickupLocation(pickupDto);
        PointDto dropOffDto = new PointDto("Point", new double[]{dropOff.getX(), dropOff.getY()});
        rideDto.setDropOffLocation(dropOffDto);
        rideDto.setOtp(null);
        return rideDto;
    }



    @Override
    @Transactional
    public RideDto startRide(Long rideId, String otp) {

//        1.get ride
        Ride ride = rideService.getRideById(rideId);
        Drivers driver = getCurrentDriver();

//        2 to check if same driver is starting
        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver can not start the ride as he has not accepted it earlier");
        }

//        3.start only if ride is confirmed

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride status is not confirmed , hence can not be started , status : " + ride.getRideStatus());
        }

//        4. check otp
        if (!ride.getOtp().equals(otp)) {
            throw new RuntimeException("OTP is not valid , otp : " + ride.getOtp());
        }
//   5.     start the ride
        ride.setStartedAt(LocalDateTime.now());
        ride.setRideStatus(RideStatus.ONGOING);
        Ride savedRide = rideRepository.save(ride);
        System.out.println("=============================================statuss    :  " + RideStatus.ONGOING + "==========================================================");
//        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);

//        6. create a payment object
        paymentService.createPayment(savedRide);

//        7. create rating
        ratingService.createNewRating(ride);

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
        Ride savedRide1 = rideRepository.save(ride);
        return rideDto;
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Drivers driver = getCurrentDriver();

        //        2 to check if same driver is starting
        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver can not start the ride as he has not accepted it earlier");
        }

//        3.end only if ride is ongoing

        if (!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException("Ride status is not ONGOING , hence can not be started , status : " + ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());

        Ride savedRide = rideService.updateRideStatus(ride,RideStatus.ENDED);
        updateDriverAvailability(driver,true);
        paymentService.processPayment(ride);
        return driverUtils.converRideTorideDto(savedRide);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
       Ride ride = rideService.getRideById(rideId);
       Drivers driver = getCurrentDriver();

//        2 to check if same driver is owning the ride
        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver is not owner of the ride");
        }

//        3.rate only if ride is confirmed

        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride status is not ended , hence can not start rating , status : " + ride.getRideStatus());
        }

//  4.   rate the rider
       Rider rider = ratingService.rateRider(ride,rating);
        return modelMapper.map(rider,RiderDto.class);

    }

    @Override
    public DriverDto getMyProfile() {
        Drivers currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver,DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {

        Drivers currentDriver = getCurrentDriver();
//        return rideService.getAllRidesOfDriver(currentDriver.getId(),pageRequest).map(
//                ride ->{
//                    return driverUtils.converRideTorideDto(ride);
//                }
//        );
        return rideService.getAllRidesOfDriver(currentDriver,pageRequest).map(
                driverUtils::converRideTorideDto
        );


    }


    @Override
    public Drivers getCurrentDriver() {

// current driver will be fetched on implementing springSecurity

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Current Driver not associated with user having id : " + user.getId()));
    }

    @Override
    public void updateDriverAvailability(Drivers driver, boolean available) {
        driver.setIs_available(available);
        driverRepository.save(driver);
    }

    @Override
    public Drivers createNewDriver(Drivers createDriver) {
        return null;
    }
}
