package ua.yatsergray.backend.v2.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.yatsergray.backend.v2.service.JwtService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Override
    public String generateUserToMusicBandInvitationToken(String userEmail, UUID musicBandId) {
        return JWT.create()
                .withSubject(userEmail)
                .withClaim("musicBandId", musicBandId.toString())
                .withExpiresAt(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .sign(getAlgorithm());
    }

    @Override
    public boolean isUserToMusicBandInvitationTokenValid(String token, String userEmail, UUID musicBandId) {
        return !isTokenExpired(token) &&
                decodeToken(token).getSubject().equals(userEmail) &&
                decodeToken(token).getClaim("musicBandId").asString().equals(musicBandId.toString());
    }

    private boolean isTokenExpired(String token) {
        return decodeToken(token).getExpiresAt().before(Date.from(Instant.now()));
    }

    private DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey.getBytes());
    }
}
