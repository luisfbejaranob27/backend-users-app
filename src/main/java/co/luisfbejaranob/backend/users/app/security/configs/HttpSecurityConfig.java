package co.luisfbejaranob.backend.users.app.security.configs;

import co.luisfbejaranob.backend.users.app.entities.enumerations.Operation;
import co.luisfbejaranob.backend.users.app.security.authentication.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig
{
    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AccessDeniedHandler accessDeniedHandler;

    public HttpSecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler)
    {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests( authReqConfig -> {
                    authReqConfig.requestMatchers(toH2Console()).permitAll();
                    authReqConfig.requestMatchers(HttpMethod.POST, "/users/register").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate-token").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.GET, "/users")
                            .hasAuthority(Operation.READ_ALL_USERS.name());
                    authReqConfig.requestMatchers(HttpMethod.GET, "/users/{id}")
                            .hasAuthority(Operation.READ_USER_BY_ID.name());
                    authReqConfig.requestMatchers(HttpMethod.GET, "/users/username/{username}")
                            .hasAuthority(Operation.READ_USER_BY_USERNAME.name());
                    authReqConfig.requestMatchers(HttpMethod.GET, "/users/profile")
                            .hasAuthority(Operation.READ_PROFILE.name());
                    authReqConfig.requestMatchers(HttpMethod.GET, "/users/exists/{filter}/{value}")
                            .hasAuthority(Operation.EXIST_USER.name());
                    authReqConfig.requestMatchers(HttpMethod.POST, "/users")
                            .hasAuthority(Operation.CREATE_USER.name());
                    authReqConfig.requestMatchers(HttpMethod.PUT, "/users/{id}")
                            .hasAuthority(Operation.UPDATE_USER.name());
                    authReqConfig.requestMatchers(HttpMethod.DELETE, "/users/{id}")
                            .hasAuthority(Operation.DELETE_USER.name());
                })
                .exceptionHandling(exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);
                    exceptionConfig.accessDeniedHandler(accessDeniedHandler);
                });

        return httpSecurity.build();
    }
}
