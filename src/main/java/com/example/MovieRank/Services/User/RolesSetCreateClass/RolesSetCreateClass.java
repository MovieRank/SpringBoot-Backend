package com.example.MovieRank.Services.User.RolesSetCreateClass;

import com.example.MovieRank.Entities.Enum.RoleEnum;
import com.example.MovieRank.Entities.Role;
import com.example.MovieRank.Exceptions.IncorrectDataInput;
import com.example.MovieRank.Repositories.RoleRepository;
import com.example.MovieRank.Services.DatabaseAnalysis.RoleAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class RolesSetCreateClass {

    static Logger logger = LoggerFactory.getLogger(RolesSetCreateClass.class);

    public static Set<Role> newUserRoles(RoleRepository roleRepository, Set<String> roles) {

        Set<Role> rolesSet = new HashSet<>();
        if (roles == null) {
            logger.error("No roles in Set");
            throw new IncorrectDataInput("Role dla użytkownika nie zostały wybrane!");
        }

        roles.forEach(role -> {
            if ("admin".equals(role)) rolesSet.add(RoleAnalysis.findRoleByName(roleRepository, RoleEnum.ROLE_ADMIN));
            else if("user".equals(role)) rolesSet.add(RoleAnalysis.findRoleByName(roleRepository, RoleEnum.ROLE_USER));
        });

        return rolesSet;
    }
}
