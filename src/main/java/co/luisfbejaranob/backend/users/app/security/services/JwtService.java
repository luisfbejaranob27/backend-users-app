package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static co.luisfbejaranob.backend.users.app.security.constants.ConstantJwtConfig.SECRET_KEY;

@Slf4j
@Service
public class JwtService
{
    @Value("${jwt.expiration}")
    private Integer expiration;

    public String generateToken(User user)
    {
        Claims claims = Jwts.claims()
                .add("authorities" , user.getAuthorities())
                .build();

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(user.getUsername())
                .issuedAt(new Date( ))
                .expiration(new Date(new Date().getTime() + expiration))
                .signWith(SECRET_KEY , Jwts.SIG.HS256)
                .claims(claims)
                .compact();
    }

    private Claims getClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token)
    {
        return getClaims(token).getSubject();
    }

    public boolean validate(String token)
    {
        try
        {
            return !getSubject(token).isBlank();
        }
        catch (ExpiredJwtException e)
        {
            log.error("token expired");
        }
        catch (UnsupportedJwtException e)
        {
            log.error("token unsupported");
        }
        catch (MalformedJwtException e)
        {
            log.error("token malformed");
        }
        catch (SignatureException e)
        {
            log.error("bad signature");
        }
        catch (IllegalArgumentException e)
        {
            log.error("illegal args");
        }
        return false;
    }
}
