package enigma.travelwise.service;

import enigma.travelwise.model.UserEntity;
import enigma.travelwise.utils.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserEntity create(UserDTO request);
    CustomPage<UserEntity> getAll(Pageable pageable, String name);
    UserEntity getById(Long id);
    UserEntity update(UserUpdateDTO request, Long id);
    String changeProfilePicture(UserChangeProfilePictureDTO request, Long id);
    String changeLocation(UserChangeLocationDTO request, Long id);
    String deleteById(Long id);
}
