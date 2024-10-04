package enigma.travelwise.controller;

import enigma.travelwise.service.AuthService;
import enigma.travelwise.utils.dto.AuthDTO;
import enigma.travelwise.utils.dto.RegisterDTO;
import enigma.travelwise.utils.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Authenticate user and return a token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO req) {
        log.info("Login request: {}", req.getUsername());
        return Response.renderJSON(
                authService.login(req)
        );
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO req) {
        return Response.renderJSON(
                authService.register(req),
                "Success",
                HttpStatus.CREATED
        );
    }

    @Operation(summary = "Refresh the authentication token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid token", content = @Content)
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            @Parameter(description = "The refresh token to use for getting a new access token")
            @RequestHeader("Authorization") String refreshToken) {
        return Response.renderJSON(
                authService.refreshToken(refreshToken)
        );
    }
}