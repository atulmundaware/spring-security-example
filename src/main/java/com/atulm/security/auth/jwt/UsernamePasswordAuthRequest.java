package com.atulm.security.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class UsernamePasswordAuthRequest {
    private String username;
    private String password;
}
