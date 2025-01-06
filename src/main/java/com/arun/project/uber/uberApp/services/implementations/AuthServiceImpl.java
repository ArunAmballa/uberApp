package com.arun.project.uber.uberApp.services.implementations;

import com.arun.project.uber.uberApp.dto.DriverDto;
import com.arun.project.uber.uberApp.dto.SignupDto;
import com.arun.project.uber.uberApp.dto.UserDto;
import com.arun.project.uber.uberApp.entities.User;
import com.arun.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.arun.project.uber.uberApp.repositories.UserRepository;
import com.arun.project.uber.uberApp.services.AuthService;
import com.arun.project.uber.uberApp.services.RiderService;
import com.arun.project.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.arun.project.uber.uberApp.entities.enums.Role.RIDER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RiderService riderService;
    private final ModelMapper modelMapper;
    private final WalletService walletService;

    @Override
    @Transactional
    public UserDto signUp(SignupDto signupDto) {

        log.info("Checking if user with Email {} already exists", signupDto.getEmail());
        Optional<User> user = userRepository.findByEmail(signupDto.getEmail());

        if(user.isPresent()) {
            log.error("User with Email {} already exists", signupDto.getEmail());
            throw new RuntimeConflictException("User With Email" + signupDto.getEmail() + " already exists");
        }
        User userToBeSaved=modelMapper.map(signupDto, User.class);
        // TODO Encode Password
        userToBeSaved.setRoles(Set.of(RIDER));
        log.info("Saving user {}",userToBeSaved);
        User savedUser = userRepository.save(userToBeSaved);

        //Making User as Rider by Default
        riderService.createRider(savedUser);

        //Creating Wallet for User
        walletService.createWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onBoardNewDriver(Long userId) {
        return null;
    }
}
