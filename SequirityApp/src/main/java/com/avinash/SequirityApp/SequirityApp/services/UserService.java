package com.avinash.SequirityApp.SequirityApp.services;

import com.avinash.SequirityApp.SequirityApp.dto.SignUpDto;
import com.avinash.SequirityApp.SequirityApp.dto.UserDto;
import com.avinash.SequirityApp.SequirityApp.entities.User;
import com.avinash.SequirityApp.SequirityApp.exception.ResourceNotFoundException;
import com.avinash.SequirityApp.SequirityApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class  UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user with given userId : " + userId +"not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
//                .orElseThrow(()-> new ResourceNotFoundException("user with email " + username + "not found"));
                .orElseThrow(()-> new BadCredentialsException("user with email " + username + "not found"));
    }

    public UserDto signUp(SignUpDto signUpDto) {

       //1. check if email already registered
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());

        if(user.isPresent())
        {
            throw new BadCredentialsException("User with given email has already exists " + signUpDto.getEmail());
        }

        User toBeCreatedUser = modelMapper.map(signUpDto,User.class);

        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));


        User savedUser = userRepository.save(toBeCreatedUser);

        return modelMapper.map(savedUser,UserDto.class);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User save(User newUser) {

        return userRepository.save(newUser);
    }
}
