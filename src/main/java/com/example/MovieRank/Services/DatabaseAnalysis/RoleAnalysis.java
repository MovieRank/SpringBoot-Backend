package com.example.MovieRank.Services.DatabaseAnalysis;

import com.example.MovieRank.Entities.Enum.RoleEnum;
import com.example.MovieRank.Entities.Role;
import com.example.MovieRank.Exceptions.RoleNotFoundException;
import com.example.MovieRank.Repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class RoleAnalysis {

    static Logger logger = LoggerFactory.getLogger(RoleAnalysis.class);

    public static Role findRoleByName(RoleRepository roleRepository, RoleEnum roleEnum) {

        Optional<Role> role = roleRepository.findRoleByName(roleEnum);
        if (role.isEmpty()) {
            logger.error("User role not found in Database");
            throw new RoleNotFoundException("Rola nie została znaleziona!");
        }

        return role.get();
    }
}
