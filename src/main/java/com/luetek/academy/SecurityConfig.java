package com.luetek.academy;

import com.luetek.academy.authentication.UsernameCredentialsService;
import com.luetek.academy.authentication.repositories.UsernamePasswordCredentialRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((auth) ->
                auth.requestMatchers("/admin/**").hasRole("ADMIN")
            )
            .formLogin(withDefaults());
        http.authorizeHttpRequests((auth) -> auth.requestMatchers("/**", "/**/*").permitAll());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UsernamePasswordCredentialRepository repository) {
        log.info("Creating Bean for UsernamePasswordCredentialService");
        return new UsernameCredentialsService(repository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Creating Bean for PasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }
}
