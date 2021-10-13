package com.example.MovieRank.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String message) {
        super(message);
    }
}
