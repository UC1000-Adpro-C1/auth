package farrel.auth.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthResponseTest {

    @Test
    public void testAuthResponse() {
        String expectedToken = "testToken";
        String expectedMessage = "testMessage";
        String expectedUsername = "testUser";
        String expectedPassword = "testPass";

        AuthResponse authResponse = new AuthResponse(expectedToken, expectedMessage, expectedUsername, expectedPassword);

        assertEquals(expectedToken, authResponse.getToken());
        assertEquals(expectedMessage, authResponse.getMessage());
        assertEquals(expectedUsername, authResponse.getUsername());
        assertEquals(expectedPassword, authResponse.getPassword());
    }
}
