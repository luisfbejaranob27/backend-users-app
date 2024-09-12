package co.luisfbejaranob.backend.users.app.utils.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiErrorDto implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    private String message;

    private List<String> errors;

    public ApiErrorDto(HttpStatus status, String message)
    {
        super();
        this.status = status;
        this.message = message;
    }

    public ApiErrorDto(HttpStatus status, String message, String error)
    {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    @JsonInclude(Include.NON_NULL)
    public String getMessage()
    {
        return message;
    }

    @JsonInclude(Include.NON_NULL)
    public List<String> getErrors()
    {
        return errors;
    }
}
