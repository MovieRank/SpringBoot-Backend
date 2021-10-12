package com.example.MovieRank.Repositories;

import com.example.MovieRank.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);
}
