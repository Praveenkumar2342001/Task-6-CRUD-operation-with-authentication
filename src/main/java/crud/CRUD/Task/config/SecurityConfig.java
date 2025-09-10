package crud.CRUD.Task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/posts/**").authenticated() // require auth for posts
                .anyRequest().permitAll()
            )
            .httpBasic(); // Use HTTP Basic Authentication

        return http.build();
    }

    // In-memory users for testing
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                               .username("user")
                               .password("password")
                               .roles("USER")
                               .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                                .username("admin")
                                .password("admin123")
                                .roles("ADMIN")
                                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
