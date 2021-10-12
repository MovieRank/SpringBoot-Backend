package com.example.MovieRank.Controllers;

import com.example.MovieRank.DTO.MessageResponse;
import com.example.MovieRank.DTO.User.Request.LoginData;
import com.example.MovieRank.DTO.User.Request.RegistrationData;
import com.example.MovieRank.DTO.User.Response.UserTokenData;
import com.example.MovieRank.Services.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping("/login")
    public ResponseEntity<UserTokenData> userLogin(@RequestBody LoginData loginData) {

        UserTokenData userTokenData = userService.userLogin(loginData);
        return ResponseEntity.ok(userTokenData);
    }

    @PostMapping("/registration")
    public ResponseEntity<MessageResponse> userRegistration(@RequestBody RegistrationData registrationData) {

        MessageResponse messageResponse = userService.userRegistration(registrationData);
        return ResponseEntity.ok(messageResponse);
    }
}
