package co.luisfbejaranob.backend.users.app.exceptions;

import co.luisfbejaranob.backend.users.app.utils.exceptions.CustomError;

public interface UserErrorsExceptions
{
    final class UsersNotFoundException extends CustomError
    {
        public UsersNotFoundException()
        {
            super("01", "Users not found");
        }
    }

    final class UserNotFoundException extends CustomError
    {
        public UserNotFoundException(String value)
        {
            super("02",
                    value.contains("@") ?
                            "User not found with email " + value :
                            value.contains("-") ?
                                    "User not found with id " + value :
                                    "User not found with username " + value
            );
        }
    }
}
