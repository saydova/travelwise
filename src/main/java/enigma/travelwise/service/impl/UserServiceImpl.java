package enigma.travelwise.service.impl;

import enigma.travelwise.model.UserEntity;
import enigma.travelwise.repository.UserRepository;
import enigma.travelwise.service.CloudinaryService;
import enigma.travelwise.service.UserService;
import enigma.travelwise.utils.dto.*;
import enigma.travelwise.utils.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity create(UserDTO request) {
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhone_number(request.getPhone_number());
        user.setProfile_picture(cloudinaryService.uploadFile(request.getProfile_picture(), "travelwise_user"));
        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());
        return userRepository.save(user);
    }

    @Override
    public CustomPage<UserEntity> getAll(Pageable pageable, String name) {
        Specification<UserEntity> specification = UserSpecification.getSpecification(name);
        Page<UserEntity> userPage = userRepository.findAll(specification, pageable);
        return new CustomPage<>(userPage);
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("USER WITH ID " + id + " NOT FOUND"));
    }

    @Override
    public UserEntity update(UserUpdateDTO request, Long id) {
        UserEntity updatedUser = this.getById(id);
        updatedUser.setName(request.getName());
        updatedUser.setEmail(request.getEmail());
        updatedUser.setPassword(request.getPassword());
        updatedUser.setPhone_number(request.getPhone_number());
        return userRepository.save(updatedUser);
    }

    @Override
    public String changeProfilePicture(UserChangeProfilePictureDTO request, Long id) {
        UserEntity changeProfile = this.getById(id);
        changeProfile.setProfile_picture(cloudinaryService.uploadFile(request.getProfile_picture(), "travelwise_user"));
        userRepository.save(changeProfile);
        String url = changeProfile.getProfile_picture();
        String result = "New profile picture : " + url;
        return result;
    }

    @Override
    public String changeLocation(UserChangeLocationDTO request, Long id) {
        UserEntity changeLocation = this.getById(id);
        changeLocation.setLatitude(request.getLatitude());
        changeLocation.setLongitude(request.getLongitude());
        userRepository.save(changeLocation);
        String result = "Newest location => latitude = " + changeLocation.getLatitude() + " | longitude = " + changeLocation.getLongitude();
        return result;
    }

    @Override
    public String deleteById(Long id) {
        UserEntity deleteUser = this.getById(id);
        userRepository.delete(deleteUser);
        String result = "User with id " + id + " deleted";
        return result;
    }
}
