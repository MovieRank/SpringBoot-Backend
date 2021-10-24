package com.example.MovieRank;

import com.example.MovieRank.Entities.Enum.RoleEnum;
import com.example.MovieRank.Entities.Role;
import com.example.MovieRank.Repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MovieRankApplication {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(MovieRankApplication.class, args);
	}

	/*
	@Bean
	InitializingBean addRolesToDatabase() {
		return () -> {
			roleRepository.save(Role.builder().name(RoleEnum.ROLE_USER).build());
			roleRepository.save(Role.builder().name(RoleEnum.ROLE_ADMIN).build());
		};
	}
	*/
}
