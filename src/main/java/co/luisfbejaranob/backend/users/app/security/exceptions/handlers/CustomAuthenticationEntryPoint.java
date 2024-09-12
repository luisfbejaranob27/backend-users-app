package co.luisfbejaranob.backend.users.app.security.exceptions.handlers;

import co.luisfbejaranob.backend.users.app.utils.exceptions.dto.ApiErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        ApiErrorDto apiError = new ApiErrorDto(
                HttpStatus.UNAUTHORIZED,
                authException.getLocalizedMessage(),
                "Authentication credentials not found");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(mapper.writeValueAsString(apiError));
    }
}
