package com.atulm.security.config;

import com.atulm.security.auth.UserAuthService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.atulm.security.config.UserPermission.*;
import static com.atulm.security.config.UserRoles.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //if we want to use @PreAuthorize on method instead of mentioning in config->antMatchers
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_STUDENT_ENDPOINT = "/admin/student/**";
    private final PasswordEncoder passwordEncoder;
    private final UserAuthService userAuthService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // If CSRF token is enable (default enable) then Spring security Adds a token in browsers cookies X-CSRF as key and expects this token in Header of any request which is not GET
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/student/**").hasRole(STUDENT.name())
//                .antMatchers("/login**").permitAll()
//                .antMatchers(HttpMethod.DELETE, ADMIN_STUDENT_ENDPOINT).hasAuthority(COURSE_WRIGHT.getPermission())
//                .antMatchers(HttpMethod.POST, ADMIN_STUDENT_ENDPOINT).hasAuthority(COURSE_WRIGHT.getPermission())
//                .antMatchers(HttpMethod.PUT, ADMIN_STUDENT_ENDPOINT).hasAuthority(COURSE_WRIGHT.getPermission())
//                .antMatchers(HttpMethod.GET, ADMIN_STUDENT_ENDPOINT).hasAnyRole(ADMIN.name(), ADMIN_TRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userAuthService);
        return daoAuthenticationProvider;
    }
}
