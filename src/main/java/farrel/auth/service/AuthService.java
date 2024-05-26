package farrel.auth.service;

import farrel.auth.model.AuthResponse;
import farrel.auth.model.Token;
import farrel.auth.model.User;
import farrel.auth.repository.TokenRepository;
import farrel.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<AuthResponse> register(User request) {
        if(repository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Username is already taken",null, null,null));
        }
        if (request.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Password must be at least 8 characters",null,null,null));
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        repository.save(user);

        return ResponseEntity.ok(new AuthResponse(null, "User registered successfully", user.getUsername(), user.getPassword(), user.getRole()));
    }

    public ResponseEntity<AuthResponse> authenticate(User request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = repository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "User not found", null, null, null));
        }
        jwtService.revokeTokenByUser(user);
        Token jwt = jwtService.saveUserToken(user);
        
        response.addHeader("Set-Cookie", "jwt=" + jwt.getToken() + "; HttpOnly; SameSite=None; Path=/");
        return ResponseEntity.ok(new AuthResponse(jwt.getToken(), "User authenticated successfully", user.getUsername(), user.getPassword(), user.getRole()));
    }
}