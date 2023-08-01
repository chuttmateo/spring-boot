package com.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/inseguro").permitAll()
                        .anyRequest().authenticated())
                .formLogin()
                    .successHandler(successHandler())
                    .permitAll()
                .and()
                    .httpBasic()//httpBasic nos permite enviar el usuario y la contraseña por en el header
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .invalidSessionUrl("/login")//no se logra crear un session, redirijo al usuario al login
                    .maximumSessions(1)
                    .expiredUrl("/login")
                .and()
                .sessionFixation() //por si el atacante se adueña de su id de session
                    .migrateSession()//spring generara otro id de session, cuando el atacante intente usar el id fijado, este ya no será vaálido
                .and()
                .build();
    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("mateo")
                .password("mateo-clave")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    public AuthenticationSuccessHandler successHandler(){
        return (((request, response, authentication) ->
                response.sendRedirect("/bienvenida")));
    }
}
