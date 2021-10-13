package com.example.MovieRank.Repositories;

import com.example.MovieRank.Entities.Enum.RoleEnum;
import com.example.MovieRank.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(RoleEnum name);
}
