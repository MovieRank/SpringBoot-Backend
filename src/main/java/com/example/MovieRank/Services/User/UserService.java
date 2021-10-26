package com.example.MovieRank.Services.User;

import com.example.MovieRank.DTO.MessageResponse;
import com.example.MovieRank.DTO.User.Request.DeleteData;
import com.example.MovieRank.DTO.User.Request.LoginData;
import com.example.MovieRank.DTO.User.Request.RegistrationData;
import com.example.MovieRank.DTO.User.Request.UpdateData;
import com.example.MovieRank.DTO.User.Response.UserImageData;
import com.example.MovieRank.DTO.User.Response.UserTokenData;
import com.example.MovieRank.Entities.User;
import com.example.MovieRank.Exceptions.IncorrectDataInput;
import com.example.MovieRank.Repositories.RoleRepository;
import com.example.MovieRank.Repositories.UserRepository;
import com.example.MovieRank.Security.TokenClass.JwtUtils;
import com.example.MovieRank.Services._DatabaseAnalysis.UserAnalysis;
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
import org.springframework.web.multipart.MultipartFile;

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

    public MessageResponse userUpdate(UpdateData updateData) {

        if (updateData.getUsername() == null || updateData.getFirstname() == null || updateData.getLastname() == null || updateData.getEmail() == null) {
            logger.error("New user data is empty");
            throw new IncorrectDataInput("Nowe dane są błędne!");
        }

        User user = UserAnalysis.findUserByUserId(userRepository, updateData.getUserId());

        String username = updateData.getUsername();
        if (!user.getUsername().equals(username) && !username.equals("")) {
            UserAnalysis.existsUserByUsername(userRepository, username);
            PatternCheckClass.usernamePattern(username);
            user.setUsername(username);
        }

        String firstname = updateData.getFirstname();
        if (!user.getFirstname().equals(firstname) && !firstname.equals("")) {
            user.setFirstname(firstname);
        }

        String lastname = updateData.getLastname();
        if (!user.getLastname().equals(lastname) && !lastname.equals("")) {
            user.setLastname(lastname);
        }

        String email = updateData.getEmail();
        if (!user.getEmail().equals(email) && !email.equals("")) {
            UserAnalysis.existsUserByEmail(userRepository, email);
            PatternCheckClass.emailPattern(email);
            user.setEmail(email);
        }

        userRepository.save(user);

        logger.error("User data successfully updated: \"" + username + "\"");
        return new MessageResponse("Dane zostały zaktualizowane.");
    }

    public MessageResponse userDelete(DeleteData deleteData) {

        AuthenticationClass.authenticateUser(authenticationManager, deleteData);
        userRepository.delete(UserAnalysis.findUserByUserId(userRepository, deleteData.getUserId()));

        logger.info("User successfully deleted: " +  "\"" + deleteData.getUsername() + "\"");
        return new MessageResponse("Konto zostało usunięte.");
    }

    public MessageResponse userUpload(Long userId, MultipartFile image) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        user.setProfileImage(ConvertToArrayBytesClass.convertNewIcon(image));

        userRepository.save(user);
        logger.info("New profile picture added correctly: " + user.getUsername());
        return new MessageResponse("Poprawnie dodano nowe zdjęcie profilowe.");
    }

    public UserImageData userGetImage(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        return UserImageData.builder().profileImage(user.getProfileImage()).build();
    }
}
