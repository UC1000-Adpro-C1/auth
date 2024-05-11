package farrel.auth.service;

import farrel.auth.model.User;
import farrel.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsImpl userDetailsImp;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
    }

    @Test
    void loadUserByUsername_success() {
        UserDetails userDetails = userDetailsImp.loadUserByUsername("testUser");
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_userNotFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());
        try {
            userDetailsImp.loadUserByUsername("testUser");
        } catch (Exception e) {
            assertEquals("User not found", e.getMessage());
        }
    }
}