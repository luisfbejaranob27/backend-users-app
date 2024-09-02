package co.luisfbejaranob.backend.users.app.security.authentication.filters;

import co.luisfbejaranob.backend.users.app.security.services.JwtService;
import co.luisfbejaranob.backend.users.app.services.UserService;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static co.luisfbejaranob.backend.users.app.security.constants.ConstantJwtConfig.HEADER_AUTHORIZATION;
import static co.luisfbejaranob.backend.users.app.security.constants.ConstantJwtConfig.PREFIX_TOKEN;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private final JwtService jwtService;

    private final UserService userService;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService)
    {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @Nullable HttpServletRequest request,
            @Nullable HttpServletResponse response,
            @Nullable FilterChain filterChain
    ) throws ServletException, IOException
    {
        assert request != null;
        String tokenHeader = request.getHeader(HEADER_AUTHORIZATION);

        if(tokenHeader != null && tokenHeader.startsWith(PREFIX_TOKEN))
        {
            String token = tokenHeader.substring(7);

            if(jwtService.validate(token))
            {
                String username = jwtService.getSubject(token);

                UserDetails userDetails = userService.findByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username , null , userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetails(request)); // Assign the request to the details (IP y SessionID)

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        assert filterChain != null;
        filterChain.doFilter(request , response);
    }
}
