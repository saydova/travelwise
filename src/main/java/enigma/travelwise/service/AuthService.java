package enigma.travelwise.service;

import enigma.travelwise.utils.dto.AuthDTO;
import enigma.travelwise.utils.dto.RegisterDTO;
import enigma.travelwise.utils.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthDTO req);
    AuthResponse register(RegisterDTO req);
    AuthResponse refreshToken(String refreshToken);
}
