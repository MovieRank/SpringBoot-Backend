package com.example.MovieRank.DTO.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationData {

    private String username;

    private String email;

    private Set<String> roles;

    private String password;
}
