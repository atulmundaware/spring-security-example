package com.atulm.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_STUDENT_ENDPOINT = "/admin/student/**";
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // If CSRF token is enable (default enable) then Spring security Adds a token in browsers cookies X-CSRF as key and expects this token in Header of any request which is not GET
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/student/**").hasRole(STUDENT.name())
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
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder().username("user").password(passwordEncoder.encode("user")).authorities(STUDENT.getGrantedAuthorities()).build();
        UserDetails admin = User.builder().username("admin").password(passwordEncoder.encode("admin")).authorities(ADMIN.getGrantedAuthorities()).build();
        UserDetails tom = User.builder().username("tom").password(passwordEncoder.encode("tom")).authorities(ADMIN_TRAINEE.getGrantedAuthorities()).build();
        return new InMemoryUserDetailsManager(userDetails, admin, tom);
    }
}
