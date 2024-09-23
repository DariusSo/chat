package com.chatroom.chat.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {



    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //authenticationProvider bean definition
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService); //set the custom user details service
//        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
//        return auth;
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.POST, "/users").anonymous()
//                        .requestMatchers(HttpMethod.GET, "/products/**").anonymous()
//                        .requestMatchers(HttpMethod.GET, "/products/category/**").anonymous()
//                        .requestMatchers(HttpMethod.GET, "/categories/get").anonymous()
//                        .requestMatchers(HttpMethod.PUT, "/users").anonymous()
//                        .requestMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")


        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf((csrf -> csrf.disable()));

        return http.build();
    }


}
