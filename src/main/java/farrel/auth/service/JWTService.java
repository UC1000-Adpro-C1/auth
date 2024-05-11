package farrel.auth.service;

import farrel.auth.model.Token;
import farrel.auth.model.User;
import farrel.auth.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${JWT_SECRET_KET}")
    private String SECRET_KEY;
    
    @Autowired
    private TokenRepository tokenRepository;

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String generateToken(User user) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
                .signWith(getSigninKey())
                .compact();

        return token;
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        Token validTokens = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        boolean validToken = !validTokens.isLoggedOut();

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    public Token saveUserToken(User user) {
        String jwt = generateToken(user);
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
        return token;
    }

    public Token revokeTokenByUser(User user) {
        Token validTokens = tokenRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        validTokens.setLoggedOut(true);

        tokenRepository.save(validTokens);

        return validTokens;
    }

}