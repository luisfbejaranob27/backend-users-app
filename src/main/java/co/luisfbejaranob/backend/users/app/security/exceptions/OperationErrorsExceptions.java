package co.luisfbejaranob.backend.users.app.security.exceptions;

import co.luisfbejaranob.backend.users.app.utils.exceptions.CustomError;

public interface OperationErrorsExceptions
{
    final class OperationFoundException extends CustomError
    {
        public OperationFoundException(Long id)
        {
            super("01" , "Operation with Id '%s' not found".formatted(id));
        }

        public OperationFoundException(String name)
        {
            super("01" , "Operation with Name '%s' not found".formatted(name));
        }
    }
}
