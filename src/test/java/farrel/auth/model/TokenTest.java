package farrel.auth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TokenTest {

    @Test
    void testToken() {
        Token token = new Token();
        User user = new User();
        user.setUsername("testUser");

        token.setId(1);
        token.setToken("testToken");
        token.setLoggedOut(false);
        token.setUser(user);

        assertEquals(1, token.getId());
        assertEquals("testToken", token.getToken());
        assertFalse(token.isLoggedOut());
        assertEquals("testUser", token.getUser().getUsername());

        // Test setters
        token.setId(2);
        token.setToken("newTestToken");
        token.setLoggedOut(true);
        User newUser = new User();
        newUser.setUsername("newTestUser");
        token.setUser(newUser);

        assertEquals(2, token.getId());
        assertEquals("newTestToken", token.getToken());
        assertEquals(true, token.isLoggedOut());
        assertEquals("newTestUser", token.getUser().getUsername());
    }
}