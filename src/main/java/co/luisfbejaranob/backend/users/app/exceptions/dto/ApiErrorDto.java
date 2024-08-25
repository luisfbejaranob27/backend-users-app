package co.luisfbejaranob.backend.users.app.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiErrorDto
{
    private HttpStatus status;
    private String code;
    private String message;
    private List<String> errors;

    public ApiErrorDto(HttpStatus status, String code, String message)
    {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ApiErrorDto(HttpStatus status, String code, String message, String error)
    {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    @JsonInclude(Include.NON_NULL)
    public String getCode()
    {
        return code;
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
