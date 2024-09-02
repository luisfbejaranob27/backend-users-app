package co.luisfbejaranob.backend.users.app.security.exceptions;

import co.luisfbejaranob.backend.users.app.utils.exceptions.CustomError;

import java.util.UUID;

public interface UserErrorsExceptions
{
    final class UserNotFoundException extends CustomError
    {
        public UserNotFoundException(UUID id)
        {
            super("01" , "User with Id '%s' not found".formatted(id));
        }

        public UserNotFoundException(String username)
        {
            super("01" , "User with Username '%s' not found".formatted(username));
        }
    }

    final class InvalidPasswordException extends CustomError
    {
        public InvalidPasswordException() {
            super("02", "Password doesn't match");
        }
    }
}
