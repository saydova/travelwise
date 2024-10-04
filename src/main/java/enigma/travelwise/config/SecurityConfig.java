package enigma.travelwise.config;

import enigma.travelwise.security.JwtAuthenticationFilter;
import enigma.travelwise.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserSecurity userSecurity;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authz -> authz
                                .requestMatchers("/api/v1/auth/**").permitAll()

//                        users
                                .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/{id}").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/change-profile-picture").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}/change-location").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/{id}").access(userAuthorizationManager())

//                        destination
                                .requestMatchers(HttpMethod.POST, "/api/v1/destinations").hasAuthority("ROLE_MITRA")
                                .requestMatchers(HttpMethod.POST, "/api/v1/destinations").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/destinations").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/v1/destinations/weather").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/v1/destinations/{id}").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.GET, "/api/v1/destinations/{id}/weather").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/destinations/{id}/photos").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/destinations/{id}").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/destinations/{id}").access(userAuthorizationManager())

//                        accommodation
                                .requestMatchers(HttpMethod.POST, "/api/v1/accommodations").hasAuthority("ROLE_MITRA")
                                .requestMatchers(HttpMethod.POST, "/api/v1/accommodations").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/accommodations").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/v1/accommodations/{id}").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/accommodations/{id}/photos").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.PUT, "/api/v1/accommodations/{id}").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/accommodations/{id}").access(userAuthorizationManager())

//                        order/transaction
                                .requestMatchers(HttpMethod.POST, "/api/v1/order_accommodations").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/order_destinations").hasAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/order_accommodations").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/v1/oder_destinations").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/order_destinations").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/order_accommodations").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/order_accommodations/{id}").access(userAuthorizationManager())
                                .requestMatchers(HttpMethod.GET, "/api/v1/order_destinations/{id}").access(userAuthorizationManager())

                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> userAuthorizationManager() {
        AuthorizationManager<RequestAuthorizationContext> adminAuth = AuthorityAuthorizationManager.hasAuthority("ROLE_ADMIN");
        AuthorizationManager<RequestAuthorizationContext> userAuth = AuthorityAuthorizationManager.hasAuthority("ROLE_USER");
        AuthorizationManager<RequestAuthorizationContext> mitraAuth = AuthorityAuthorizationManager.hasAuthority("ROLE_MITRA");

        return (authentication, context) -> {
            if (adminAuth.check(authentication, context).isGranted()) {
                return new AuthorizationDecision(true);
            } else {
                try {
                    Long userId = Long.parseLong(context.getVariables().get("id"));

                    if (userAuth.check(authentication, context).isGranted() && userSecurity.hasUserId(authentication.get(), userId)) {
                        return new AuthorizationDecision(true);
                    }

                    if (mitraAuth.check(authentication, context).isGranted() && userSecurity.hasUserId(authentication.get(), userId)) {
                        return new AuthorizationDecision(true);
                    }

                    return new AuthorizationDecision(false);
                } catch (NumberFormatException e) {
                    return new AuthorizationDecision(false);
                }
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Add your frontend URL here
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}