package co.luisfbejaranob.backend.users.app.security.exceptions;

import co.luisfbejaranob.backend.users.app.utils.exceptions.CustomError;

public interface ModuleErrorsExceptions
{
    final class ModuleNotFoundException extends CustomError
    {
        public ModuleNotFoundException(Long id)
        {
            super("01" , "Module with Id '%s' not found".formatted(id));
        }

        public ModuleNotFoundException(String name)
        {
            super("01" , "Module with Name '%s' not found".formatted(name));
        }
    }
}
