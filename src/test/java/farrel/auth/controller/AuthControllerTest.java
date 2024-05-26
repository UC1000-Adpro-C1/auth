package farrel.auth.controller;

import farrel.auth.model.AuthResponse;
import farrel.auth.model.User;
import farrel.auth.restcontroller.AuthRestController;
import farrel.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthRestController authController;

    @Mock
    private AuthService authService;


    @Test
    public void testRegister() {
        User user = new User();
        AuthResponse expectedResponse = new AuthResponse("testToken", "User registered successfully", user.getUsername(), user.getPassword());
        when(authService.register(user)).thenReturn(ResponseEntity.ok(expectedResponse));

        ResponseEntity<AuthResponse> response = authController.register(user);

        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testLogin() {
        User user = new User();
        AuthResponse expectedResponse = new AuthResponse("testToken", "User registered successfully", user.getUsername(), user.getPassword());
        when(authService.authenticate(user, null)).thenReturn(ResponseEntity.ok(expectedResponse));

        ResponseEntity<AuthResponse> response = authController.login(user, null);

        assertEquals(expectedResponse, response.getBody());
    }
}