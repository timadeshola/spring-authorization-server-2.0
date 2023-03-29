package com.example.oauthserver.config;

import com.example.oauthserver.core.security.CustomCorsFilter;
import com.example.oauthserver.core.security.CustomAuthenticationProvider;
import com.example.oauthserver.model.enums.AppStatus;
import com.example.oauthserver.persistence.entity.Privilege;
import com.example.oauthserver.persistence.entity.Role;
import com.example.oauthserver.persistence.repository.PrivilegeRepository;
import com.example.oauthserver.persistence.repository.RoleRepository;
import com.example.oauthserver.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * Project title: oauth-server
 *
 * @author johnadeshola
 * Date: 3/24/23
 * Time: 10:08 AM
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final CustomAuthenticationProvider authenticationProvider;

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .headers().defaultsDisabled().cacheControl()
                .and().xssProtection().disable()
                .frameOptions().sameOrigin().httpStrictTransportSecurity().disable()
                .and()
                .rememberMe().key("remember-me").tokenValiditySeconds(Duration.ofDays(14L).toSecondsPart())
                .and().addFilterBefore(new CustomCorsFilter(), ChannelProcessingFilter.class);
        return http.build();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public com.example.oauthserver.persistence.entity.User user() {
        return userRepository.save(com.example.oauthserver.persistence.entity.User.builder()
                .username("admin")
                .password(passwordEncoder.encode("welcome"))
                .name("John Doe")
                .status(AppStatus.ACTIVE)
                .roles(new HashSet<>(roleRepository.saveAll(Set.of(Role.builder()
                        .name("ADMIN")
                        .description("Admin role")
                        .status(AppStatus.ACTIVE)
                        .privileges(new HashSet<>(privilegeRepository.saveAll(Set.of(Privilege.builder()
                                .name("ADMIN")
                                .description("Admin privilege")
                                .status(AppStatus.ACTIVE)
                                .build()))))
                        .build()))))
                .build());
    }
}
