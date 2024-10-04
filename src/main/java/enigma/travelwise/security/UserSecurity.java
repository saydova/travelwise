package enigma.travelwise.security;

import enigma.travelwise.model.UserEntity;
import enigma.travelwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSecurity {
    private final UserRepository userRepository;

    public boolean hasUserId(Authentication authentication, Long userId) {
        UserEntity user = userRepository.findByUsername(authentication.getName()).orElse(null);
        Long currentUserId = user.getId();
        System.out.println("bbbbb");
        System.out.println(currentUserId);
        System.out.println(userId);
        return currentUserId.equals(userId);
    }

    public boolean isUser(Authentication authentication, int userId) {
        System.out.println("cek " + authentication.isAuthenticated());
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        System.out.println(principal instanceof UserEntity);

        if (!(principal instanceof User)) {
            return false;
        }
        UserEntity user = (UserEntity) principal;
        return user.getId() == userId;
    }
}
