package com.example.MovieRank.Services.DatabaseAnalysis;

import com.example.MovieRank.Exceptions.UserAlreadyExistsException;
import com.example.MovieRank.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAnalysis {

    static Logger logger = LoggerFactory.getLogger(UserAnalysis.class);

    public static void existsUserByUsername(UserRepository userRepository, String username) {

        if (userRepository.existsUserByUsername(username)) {
            logger.error(("User with the given name already exists in Database: " + "\"" + username + "\""));
            throw new UserAlreadyExistsException("Użytkownik o podanej nazwie już istnieje!");
        }
    }

    public static void existsUserByEmail(UserRepository userRepository, String email) {

        if (userRepository.existsUserByEmail(email)) {
            logger.error("User with the given e-mail already exists in Database: " + "\"" + email + "\"");
            throw new UserAlreadyExistsException("Użytkownik o podanym e-mailu już istnieje!");
        }
    }
}
