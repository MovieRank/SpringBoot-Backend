package com.example.MovieRank.DTO.User.Response;

import com.example.MovieRank.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData {

    private Long userId;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private Collection<? extends GrantedAuthority> roles;
}
