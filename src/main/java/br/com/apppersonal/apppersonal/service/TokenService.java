package br.com.apppersonal.apppersonal.service;

import br.com.apppersonal.apppersonal.model.Dto.TimesTampAndEmailDto;
import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.utils.Base64Code;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class TokenService {
    private final Base64Code base64Code;

    @Autowired
    public TokenService(Base64Code base64Code) {
        this.base64Code = base64Code;
    }

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Generate a new token.
     **/
    public String generateToken(UserDetails userDetails, Map<String, Object> additionalData) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("app-personal")
                .withSubject(userDetails.getUsername())
                .withPayload(additionalData)
                .withExpiresAt(Date.from(generateExpirationDate()))
                .sign(algorithm);
    }


    /**
     * Generate expirationToke.
     **/
    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }


    /**
     * validate Token.
     **/
    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("app-personal")
                .build()
                .verify(token)
                .getSubject();
    }

    @SneakyThrows
    public String generateTokenToResetPassword(String email, String password) {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();

        tokenService.setServerSecret(password);
        tokenService.setServerInteger(3);
        tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());

        Token token = tokenService.allocateToken(email);

        return token.getKey();
    }
}
