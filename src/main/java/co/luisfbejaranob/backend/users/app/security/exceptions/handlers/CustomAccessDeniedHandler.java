package co.luisfbejaranob.backend.users.app.security.exceptions.handlers;

import co.luisfbejaranob.backend.users.app.utils.exceptions.dto.ApiErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{
    private final ObjectMapper mapper;

    public CustomAccessDeniedHandler(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        ApiErrorDto apiError = new ApiErrorDto(
                HttpStatus.FORBIDDEN,
                accessDeniedException.getLocalizedMessage(),
                "You do not have the permissions to execute the request");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(mapper.writeValueAsString(apiError));
    }
}
