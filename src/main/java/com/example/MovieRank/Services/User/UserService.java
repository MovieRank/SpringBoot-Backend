package com.example.MovieRank.Services.User;

import com.example.MovieRank.DTO.MessageResponse;
import com.example.MovieRank.DTO.User.Request.LoginData;
import com.example.MovieRank.DTO.User.Request.RegistrationData;
import com.example.MovieRank.DTO.User.Response.UserTokenData;
import com.example.MovieRank.Entities.User;
import com.example.MovieRank.Repositories.RoleRepository;
import com.example.MovieRank.Repositories.UserRepository;
import com.example.MovieRank.Security.TokenClass.JwtUtils;
import com.example.MovieRank.Services.DatabaseAnalysis.UserAnalysis;
import com.example.MovieRank.Services.User.AuthenticationClass.AuthenticationClass;
import com.example.MovieRank.Services.User.ConvertToArrayBytesClass.ConvertToArrayBytesClass;
import com.example.MovieRank.Services.User.PatternCheckClass.PatternCheckClass;
import com.example.MovieRank.Services.User.RolesSetCreateClass.RolesSetCreateClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserTokenData userLogin(LoginData loginData) {

        Authentication authentication = AuthenticationClass.authenticateUser(authenticationManager, loginData);
        UserTokenData userTokenData = AuthenticationClass.getUserData(authentication, jwtUtils);

        logger.info("User successfully logged in: " +  "\"" + loginData.getUsername() + "\"");
        return userTokenData;
    }

    public MessageResponse userRegistration(RegistrationData registrationData) {

        String username = registrationData.getUsername();
        String email = registrationData.getEmail();
        String password = registrationData.getPassword();

        UserAnalysis.existsUserByUsername(userRepository, username);
        UserAnalysis.existsUserByEmail(userRepository, email);

        PatternCheckClass.usernamePattern(username);
        PatternCheckClass.emailPattern(email);
        PatternCheckClass.passwordPattern(password);

        userRepository.save(User.builder()
                .username(username)
                .firstname("")
                .lastname("")
                .email(email)
                .password(passwordEncoder.encode(password))
                .profileImage(ConvertToArrayBytesClass.convertDefaultIcon())
                .roles(RolesSetCreateClass.newUserRoles(roleRepository, registrationData.getRoles()))
                .build());

        logger.info("User successfully registered: \"" + username + "\"");
        return new MessageResponse("Rejestracja przebiegła pomyślnie.");
    }
}
