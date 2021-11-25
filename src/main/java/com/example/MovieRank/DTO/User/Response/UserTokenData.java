package com.example.MovieRank.DTO.User.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTokenData {

    private Long userId;

    private String username;

    private String token;

    private String type;
}
