package me.hwibaski.securityinaction.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * // WebSecurityConfigurerAdapter deprecated에 따른 권장 방법 소개 글
 * https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * https://covenant.tistory.com/277
 */

@Configuration
public class SecurityConfig {
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails userDetails = User.withUsername("hwibaski").password("12345").authorities("read").build();
    //
    //     return new InMemoryUserDetailsManager(userDetails);
    // }
    //
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return NoOpPasswordEncoder.getInstance();
    // }

    // 위의 PasswordEncoder 설정과 UserDetailService Bean을 한 번에 정의 가능
    // 하지만 하나의 Bean에서 UserDetailsService bean과 PasswordEncoder 빈을 모두 설정하고 있으므로
    // 바람직한 설정 방법은 아니다.
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("hwibaski")
            .password("12345")
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 모든 요청에 인증 요구
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());

        //        모든 요청 허용
        //        http
        //                .authorizeHttpRequests(authz -> authz
        //                        .anyRequest().permitAll()
        //                )
        //                .httpBasic(withDefaults());

        return http.build();
    }
}

// @Configuration
// public class SecurityConfig extends WebSecurityConfigurerAdapter {
//     @Bean
//     public UserDetailsService userDetailsService() {
//         UserDetails userDetails = User.withUsername("hwibaski").password("12345").authorities("read").build();
//
//         return new InMemoryUserDetailsManager(userDetails);
//     }
//
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return NoOpPasswordEncoder.getInstance();
//     }
//
//
//     // 이 코드로도 UserDetailService와 PasswordEncoder를 설정할 수 있다.
//     // 하지만 WebSecurityConfigurerAdapter 자체가 deprecated 되었으므로 참고 정도만 하자.
//
//     // @Override
//     // protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//     //     UserDetails userDetails = User.withUsername("hwibaski").password("12345").authorities("read").build();
//     //
//     //     authenticationManagerBuilder.userDetailsService(new InMemoryUserDetailsManager(userDetails))
//     //         .passwordEncoder(NoOpPasswordEncoder.getInstance());
//     // }
//
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.httpBasic();
//         // 모든 요청에 인증이 필요하다.
//         http.authorizeHttpRequests().anyRequest().authenticated();
//         // 모든 요청을 인증없이 허가함
//         // http.authorizeHttpRequests().anyRequest().permitAll();
//     }
// }
