package co.luisfbejaranob.backend.users.app.utils.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public abstract class CustomError extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 1L;

    private final String code;

    protected CustomError(String code, String message)
    {
        super(message);
        this.code = code;
    }

    protected CustomError(String code, String message, Throwable cause)
    {
        super(message, cause);
        this.code = code;
    }
}
