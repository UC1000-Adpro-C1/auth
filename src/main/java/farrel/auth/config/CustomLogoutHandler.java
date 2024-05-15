package farrel.auth.config;

import farrel.auth.model.Token;
import farrel.auth.repository.TokenRepository;
import farrel.auth.service.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    private final JWTService jwtService;

    public CustomLogoutHandler(TokenRepository tokenRepository, JWTService jwtService) {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                token = cookie.getValue();
                break;
            }
        }

        if(token == null) {
            return;
        }

        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if(storedToken != null) {
            response.addHeader("Set-Cookie", "jwt=; HttpOnly; SameSite=None; Secure; Path=/; Max-Age=0");
            jwtService.revokeTokenByUser(storedToken.getUser());
        }
    }
}