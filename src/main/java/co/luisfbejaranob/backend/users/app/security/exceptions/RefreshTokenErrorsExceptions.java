package co.luisfbejaranob.backend.users.app.security.exceptions;

public interface RefreshTokenErrorsExceptions
{
    final class RefreshTokenNotFoundException extends RuntimeException
    {
        public RefreshTokenNotFoundException(String token) {
            super("Refresh Token with Id '%s' not found".formatted(token));
        }
    }

    final class RefreshTokenExpiredException extends RuntimeException
    {
        public RefreshTokenExpiredException()
        {
            super("Refresh Token expired");
        }
    }
}
