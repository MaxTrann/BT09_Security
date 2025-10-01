package vn.maxtrann.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // để dùng @PreAuthorize ở controller
public class SecurityConfig {

  // 2 user in-memory: admin(ROLE_ADMIN), user(ROLE_USER)
  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder pe) {
    UserDetails admin = User.withUsername("admin")
        .password(pe.encode("123456"))
        .roles("ADMIN")
        .build();

    UserDetails user = User.withUsername("user")
        .password(pe.encode("123456"))
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(admin, user);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // demo nhanh
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/hello").permitAll()
          // để @PreAuthorize quyết định chi tiết quyền cho /customer/**
          .requestMatchers("/customer/**").authenticated()
          .anyRequest().authenticated()
      )
      .formLogin(Customizer.withDefaults())
      .logout(Customizer.withDefaults());
    return http.build();
  }
}
