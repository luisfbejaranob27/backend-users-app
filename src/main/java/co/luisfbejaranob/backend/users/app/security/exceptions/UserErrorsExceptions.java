package co.luisfbejaranob.backend.users.app.security.exceptions;

import co.luisfbejaranob.backend.users.app.utils.exceptions.CustomError;

public interface UserErrorsExceptions
{

    final class InvalidPasswordException extends CustomError
    {
        public InvalidPasswordException() {
            super("02", "Password doesn't match");
        }
    }
}
