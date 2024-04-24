package de.sremer.crawlicious.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Qualifier("userService")
    private final UserDetailsService userDetailsService;

    @Value("${rememberme-key}")
    private String rememberMeKey;

    @Bean
    public SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
        return http
                .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                        .rememberMeCookieName("woofles-remember-me")
                        .key(rememberMeKey)
                        .tokenValiditySeconds(60 * 60 * 24 * 365))

                .addFilter(switchUserFilter())
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(loginCustomizer -> loginCustomizer
                        .loginPage("/login").failureUrl("/?login=error")
                        .defaultSuccessUrl("/profile")
                        .usernameParameter("email")
                        .passwordParameter("password"))

                .logout(logoutCustomizer -> logoutCustomizer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/"))

                .exceptionHandling(exceptionCustomizer -> exceptionCustomizer
                        .accessDeniedPage("/access-denied"))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("favicon.ico", "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/", "/login", "/profile/**", "/test").permitAll()
                        .requestMatchers("/imprint").permitAll()
                        .requestMatchers("/registration", "/password_reset", "/password_reset_token").permitAll()
                        .requestMatchers("/password_update").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "PREVIOUS_ADMINISTRATOR")
                        .anyRequest().authenticated())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SwitchUserFilter switchUserFilter() {
        SwitchUserFilter filter = new SwitchUserFilter();
        filter.setUserDetailsService(userDetailsService);
        filter.setTargetUrl("/");
        filter.setSwitchUserUrl("/admin/switch/login");
        filter.setUsernameParameter("email");
        filter.setExitUserUrl("/admin/switch/logout");
        return filter;
    }
}

