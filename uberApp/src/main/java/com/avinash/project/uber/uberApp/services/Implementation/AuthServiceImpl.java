package com.avinash.project.uber.uberApp.services.Implementation;

import com.avinash.project.uber.uberApp.dto.DriverDto;
import com.avinash.project.uber.uberApp.dto.SignUpDto;
import com.avinash.project.uber.uberApp.dto.UserDto;
import com.avinash.project.uber.uberApp.entities.Drivers;
import com.avinash.project.uber.uberApp.entities.User;
import com.avinash.project.uber.uberApp.entities.enums.Role;
import com.avinash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.avinash.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.avinash.project.uber.uberApp.repositories.UserRepository;
import com.avinash.project.uber.uberApp.security.JWTService;
import com.avinash.project.uber.uberApp.services.AuthService;
import com.avinash.project.uber.uberApp.services.DriverService;
import com.avinash.project.uber.uberApp.services.RiderService;
import com.avinash.project.uber.uberApp.services.WalletService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Data
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RiderService riderService;
    private final ModelMapper modelMapper;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String username, String password) {

        String tokens[] = new String[2];

        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

        User user = (User) authentication.getPrincipal();                                  /// pricipal => it'suser-object , it is saved in AuthFilter in UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

        String accessToken = jwtService.getAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken,refreshToken};
    }

    @Override
    @Transactional
    //// it will ensure either all statements of the method(signUp) will run or not any and roll back to initial position(like transaction => complete or roll-back)
    public UserDto signUp(SignUpDto signUpDto) {

        User user = userRepository.findByEmail(signUpDto.getEmail());
        if (user != null)
            throw new RuntimeConflictException("Cannot signup, User already exists with email " + signUpDto.getEmail());

        User mappedUser = modelMapper.map(signUpDto, User.class);
        mappedUser.setUser_roles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));    //// encode the password just before saving it into db
        User savedUser = userRepository.save(mappedUser);

//        create user related entities
        riderService.createNewRider(savedUser);
        walletService.createWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }





    @Override
    public DriverDto onBoardNewDriver(Long userId, String vehicleId) {
      User user = userRepository.findById(userId).
              orElseThrow(()->new ResourceNotFoundException("user not found with id : "+ userId));

      if(user.getUser_roles().contains(Role.DRIVER))
                throw new RuntimeConflictException("User with id " + userId + " is already a Driver");

        Drivers createDriver = Drivers.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .is_available(true)
                .currentLocation(null)
                .build();

        user.getUser_roles().add(Role.DRIVER);
        userRepository.save(user);
        Drivers savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver,DriverDto.class);
    }




    @Override
    public String refreshToken(String refreshToken)
    {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with id " + userId));

        return jwtService.getAccessToken(user);
//        check

    }
}
