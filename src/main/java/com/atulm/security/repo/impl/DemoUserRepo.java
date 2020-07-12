package com.atulm.security.repo.impl;

import com.atulm.security.auth.UserAuthDetails;
import com.atulm.security.config.UserRoles;
import com.atulm.security.repo.UserRepo;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DemoUserRepo implements UserRepo {

    private final PasswordEncoder passwordEncoder;

    //replace it with database layer implementation
    @Override
    public Optional<UserAuthDetails> findUserByUserName(String username) {
        return getUserAuthDetails().stream().filter(u->u.getUsername().equals(username)).findFirst();
    }

    private List<UserAuthDetails> getUserAuthDetails() {
        return Lists.newArrayList(
                new UserAuthDetails(UserRoles.STUDENT.getGrantedAuthorities(),"user",passwordEncoder.encode("user"),true,true,true,true),
                new UserAuthDetails(UserRoles.ADMIN.getGrantedAuthorities(),"admin",passwordEncoder.encode("user"),true,true,true,true),
                new UserAuthDetails(UserRoles.ADMIN_TRAINEE.getGrantedAuthorities(),"tom",passwordEncoder.encode("user"),true,true,true,true)
        );
    }

}
