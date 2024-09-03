package co.luisfbejaranob.backend.users.app.security.exceptions;

import co.luisfbejaranob.backend.users.app.utils.exceptions.CustomError;

public interface GrantedPermissionErrorsExceptions
{
    final class GrantedPermissionNotFoundException extends CustomError
    {
        public GrantedPermissionNotFoundException(long id)
        {
            super("01" , "Granted Permission with Id '%s' not found".formatted(id));
        }
    }
}
