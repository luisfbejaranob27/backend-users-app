package co.luisfbejaranob.backend.users.app.security.constants;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public final class ConstantJwtConfig {

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";

    public static final String HEADER_AUTHORIZATION = "Authorization";

    private ConstantJwtConfig()
    {}
}
