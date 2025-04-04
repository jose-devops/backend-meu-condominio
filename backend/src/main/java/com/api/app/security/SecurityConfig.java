package com.api.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Desabilita CSRF (não necessário para JWT)
                .authorizeHttpRequests()  // Usando o método correto para versões 6.x
                .requestMatchers("/usuario/cadastrar", "/usuario/login").permitAll()  // Permite acesso sem autenticação para cadastrar e login
                .requestMatchers("/api/proprietario/**").hasRole("PROPRIETARIO")   // Restringe para o Proprietário
                .requestMatchers("/api/inquilino/**").hasRole("INQUILINO")         // Restringe para o Inquilino
                .anyRequest().authenticated()  // Exige autenticação para outras rotas
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // Sem sessões (usando JWT)

        // Adiciona o filtro JWT antes do filtro de autenticação padrão
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}


