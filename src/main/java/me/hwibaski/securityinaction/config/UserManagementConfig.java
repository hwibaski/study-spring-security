package me.hwibaski.securityinaction.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import me.hwibaski.securityinaction.model.JpaUser;
import me.hwibaski.securityinaction.model.SecurityUser;
import me.hwibaski.securityinaction.service.InMemoryUserDetailsService;

/**
 * AuthenticationProvider를 구현한 구현체가 있으면 아래의 설정이 적용되지 않는다.
 */
@Configuration
public class UserManagementConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        JpaUser jpaUser = new JpaUser("hwibaski", "12345", "read");
        UserDetails userDetails = new SecurityUser(jpaUser);

        List<UserDetails> users= List.of(userDetails);
        return new InMemoryUserDetailsService(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
