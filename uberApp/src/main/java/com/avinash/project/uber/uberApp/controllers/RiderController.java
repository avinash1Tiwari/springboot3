package com.avinash.project.uber.uberApp.controllers;


import com.avinash.project.uber.uberApp.advices.ApiResponse;
import com.avinash.project.uber.uberApp.dto.*;
import com.avinash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.avinash.project.uber.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
@Secured("ROLE_RIDER")
public class RiderController {


    private final RiderService riderService;


    @PostMapping("/requestRide")
    public ResponseEntity<ApiResponse<RideRequestDto>> requestRide(@RequestBody RideRequestDto rideRequestDto) {
        return riderService.requestRide(rideRequestDto);
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId)
    {
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @PostMapping("/rateDriver")
    public ResponseEntity<DriverDto> rateDto(@RequestBody RatingDto ratingDto)
    {
        if(ratingDto == null || ratingDto.getRating() == null || ratingDto.getRideId() == null)
        {
            throw  new ResourceNotFoundException("some fields are missing");
        }

        return ResponseEntity.ok(riderService.rateDriver(ratingDto.getRideId(),ratingDto.getRating()));
    }


    @GetMapping("/getMyProfile")
    ResponseEntity<RiderDto> getMyProfile()
    {
        return  ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                 @RequestParam(defaultValue = "10",required = false) Integer pageSize)
    {
        PageRequest pageRequest = PageRequest.of(pageOffSet,pageSize, Sort.Direction.DESC,"createdTime","id");

        return ResponseEntity.ok(riderService.getAllMyRides(pageRequest));
    }


//    @PostMapping("/rateDriver/{rideId}/{rating}")
//    public ResponseEntity<DriverDto> rateDriver(@PathVariable Long rideId,@PathVariable Integer rating)
//    {
//        return ResponseEntity.ok(riderService.rateDriver(rideId,rating));
//    }
}
