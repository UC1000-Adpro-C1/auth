package farrel.auth.service;

import farrel.auth.model.Token;
import farrel.auth.model.User;
import farrel.auth.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {

    @Mock
    private TokenRepository tokenRepository;
    @InjectMocks
    private JWTService jwtService;

    private Token token;
    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername("testUser");
        token = jwtService.saveUserToken(user);
    }

    @Test
    public void testExtractUsername() {
        String actualUsername = jwtService.extractUsername(token.getToken());
        assertEquals(user.getUsername(), actualUsername);
    }

    @Test
    public void testIsValid() {
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));
        boolean isValid = jwtService.isValid(token.getToken(), user);
        assertTrue(isValid);
    }

    @Test
    public void testIsValidThrowsException() {
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> jwtService.isValid(token.getToken(), user));
    }

    @Test
    public void testIsNotValid() {
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));
        User user = new User();
        user.setUsername("testUser2");
        boolean isValid = jwtService.isValid(token.getToken(), user);
        assertFalse(isValid);
    }

    @Test
    public void testIsNotValidLoggedOut() {
        token.setLoggedOut(true);
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));
        boolean isValid = jwtService.isValid(token.getToken(), user);
        assertFalse(isValid);
    }


    @Test
    public void testSaveUserToken() {
        Token token = jwtService.saveUserToken(user);
        verify(tokenRepository, times(1)).save(token);
    }

    @Test
    public void testRevokeTokenByUser() {
        when(tokenRepository.findByUser(user)).thenReturn(Optional.of(token));
        Token revokedToken = jwtService.revokeTokenByUser(user);
        assertEquals(token.getToken(), revokedToken.getToken());
        assertTrue(revokedToken.isLoggedOut());
    }

    @Test
    public void testRevokeTokenByUserThrowsException() {
        User user = new User();
        user.setUsername("testUsername");

        when(tokenRepository.findByUser(user)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> jwtService.revokeTokenByUser(user));
    }
}