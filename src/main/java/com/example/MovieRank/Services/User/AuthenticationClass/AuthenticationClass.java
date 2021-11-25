package com.example.MovieRank.Services.User.AuthenticationClass;

import com.example.MovieRank.DTO.User.Request.DeleteData;
import com.example.MovieRank.DTO.User.Request.LoginData;
import com.example.MovieRank.DTO.User.Response.UserTokenData;
import com.example.MovieRank.Security.TokenClass.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationClass {

    public static Authentication authenticateUser(AuthenticationManager authenticationManager, LoginData loginData) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public static void authenticateUser(AuthenticationManager authenticationManager, DeleteData deleteData) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(deleteData.getUsername(), deleteData.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static String getUserData(Authentication authentication, JwtUtils jwtUtils) {

        return jwtUtils.generateJwtToken(authentication);
    }
}
