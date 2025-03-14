package com.example.fanatasia.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.fanatasia.models.User;
import com.example.fanatasia.repositoryes.UserRepository;

import java.util.Collections;
import java.util.List;

/**
 * Configurazione di Spring Security per gestire l'autenticazione e
 * l'autorizzazione.
 */
@Configuration
@RequiredArgsConstructor

public class SecurityConfig {
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("Utente non trovato con username: " + username);
            }
            // Convertiamo il ruolo enum in una stringa compatibile con Spring Security
            List<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority(user.getRole().name()));

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities);
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disabilitiamo il CSRF per semplificare (in produzione valutare attentamente
                // questa scelta)
                .csrf(csrf -> csrf.disable())
                // Configuriamo le autorizzazioni: le pagine di registrazione e login sono
                // accessibili a tutti
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Accesso solo per admin
                        .anyRequest().authenticated())
                // Configuriamo il form di login
                .formLogin(form -> form
                        .loginPage("/auth/login") // Pagina personalizzata di login
                        .loginProcessingUrl("/auth/login") // URL a cui il form invia i dati
                        .successHandler((request, response, authentication) -> {
                            // Controlliamo il ruolo dell'utente
                            String redirectUrl = "/creature"; // Redirect di default per utenti normali
                            if (authentication.getAuthorities().stream()
                                    .anyMatch(
                                            grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                                redirectUrl = "/admin/creature"; // Redirect per admin
                            }

                            // Eseguiamo il redirect
                            response.sendRedirect(redirectUrl);
                        }) // Gestore di successo personalizzato per il login
                        .permitAll())
                // Configuriamo il logout
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll())
                // Configuriamo il provider di autenticazione
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

}
