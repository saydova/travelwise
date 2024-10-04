package enigma.travelwise.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import enigma.travelwise.service.AuthService;
import enigma.travelwise.utils.dto.AuthDTO;
import enigma.travelwise.utils.dto.RegisterDTO;
import enigma.travelwise.utils.response.AuthResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        AuthDTO authDTO = new AuthDTO("testuser", "password");
        AuthResponse authResponse = new AuthResponse("access_token", "refresh_token");
        when(authService.login(authDTO)).thenReturn(authResponse);

        ResponseEntity<?> response = authController.login(authDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Add more assertions based on your Response class structure
    }

    @Test
    void testRegister() {
        RegisterDTO registerDTO = new RegisterDTO("Test User", "testuser", "test@example.com", "Password123!", "1234567890", null);
        AuthResponse authResponse = new AuthResponse("access_token", "refresh_token");
        when(authService.register(registerDTO)).thenReturn(authResponse);

        ResponseEntity<?> response = authController.register(registerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        // Add more assertions based on your Response class structure
    }
}