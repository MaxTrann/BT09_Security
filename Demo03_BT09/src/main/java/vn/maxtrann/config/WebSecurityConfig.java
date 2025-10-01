package vn.maxtrann.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import vn.maxtrann.service.impl.CustomUserDetailsService;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 10 rounds
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(customUserDetailsService);
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(auth -> auth
            // CHO PHÉP API
            .requestMatchers("/api/**").permitAll()
            
            // WEB theo slide (giữ nguyên nếu bạn có trang thymeleaf)
            .requestMatchers("/").hasAnyAuthority("USER","ADMIN","EDITOR","CREATOR")
            .requestMatchers("/new").hasAnyAuthority("ADMIN","CREATOR")
            .requestMatchers("/edit/**").hasAnyAuthority("ADMIN","EDITOR")
            .requestMatchers("/delete/**").hasAuthority("ADMIN")
            .requestMatchers("/login", "/403", "/css/**", "/js/**", "/images/**").permitAll()

            .anyRequest().authenticated()
        );
        http.httpBasic(Customizer.withDefaults());
        http.formLogin(f -> f.loginPage("/login").defaultSuccessUrl("/", true).permitAll());
        http.logout(l -> l.permitAll());
        http.exceptionHandling(e -> e.accessDeniedPage("/403"));

        http.authenticationProvider(authenticationProvider());
        return http.build();
    }
}
