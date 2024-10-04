package enigma.travelwise.service;

import enigma.travelwise.model.UserEntity;
import enigma.travelwise.repository.UserRepository;
import enigma.travelwise.service.impl.UserServiceImpl;
import enigma.travelwise.utils.dto.CustomPage;
import enigma.travelwise.utils.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setPhone_number("1234567890");
        MultipartFile mockFile = mock(MultipartFile.class);
        userDTO.setProfile_picture(mockFile);

        when(cloudinaryService.uploadFile(any(MultipartFile.class), eq("travelwise_user"))).thenReturn("image_url");
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        UserEntity result = userService.create(userDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(userDTO.getName(), result.getName());
        assertEquals("image_url", result.getProfile_picture());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

//    @Test
//    void testGetAll() {
//        Pageable pageable = mock(Pageable.class);
//        List<UserEntity> users = Arrays.asList(new UserEntity(), new UserEntity());
//        Page<UserEntity> page = new PageImpl<>(users);
//
//        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
//
//        CustomPage<UserEntity> result = userService.getAll(pageable, null);
//
//        assertNotNull(result);
//        assertEquals(2, result.getContent().size());
//        verify(userRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
//    }

    @Test
    void testGetById() {
        Long id = 1L;
        UserEntity user = new UserEntity();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserEntity result = userService.getById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(userRepository, times(1)).findById(id);
    }

    // Add more tests for other methods...
}