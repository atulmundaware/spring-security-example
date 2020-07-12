package com.atulm.security.repo;

import com.atulm.security.auth.UserAuthDetails;

import java.util.Optional;

public interface UserRepo  {

    Optional<UserAuthDetails> findUserByUserName(String username);

}
