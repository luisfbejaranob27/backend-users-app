package co.luisfbejaranob.backend.users.app.security.exceptions;

import co.luisfbejaranob.backend.users.app.utils.exceptions.CustomError;

public interface RoleErrorsExceptions
{
    final class RoleNotFoundException extends CustomError
    {
        public RoleNotFoundException(Long id)
        {
            super("01" , "Role with Id '%s' not found".formatted(id));
        }

        public RoleNotFoundException(String name)
        {
            super("01" , "Role with Name '%s' not found".formatted(name));
        }
    }
}
