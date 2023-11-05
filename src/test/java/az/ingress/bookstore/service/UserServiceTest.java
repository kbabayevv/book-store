package az.ingress.bookstore.service;

import az.ingress.bookstore.entity.User;
import az.ingress.bookstore.repository.UserRepository;
import az.ingress.bookstore.rest.dto.request.UserRequestDTO;
import az.ingress.bookstore.rest.dto.response.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldSaveUser() {
        // Arrange
        UserRequestDTO userRequestDTO = new UserRequestDTO();

        User user = new User();

        when(modelMapper.map(userRequestDTO, User.class)).thenReturn(user);
        when(passwordEncoder.encode(userRequestDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        // Act
        UserResponseDTO userResponseDTO = userService.save(userRequestDTO);

        // Assert
        assertEquals(userResponseDTO.getId(), user.getId());

        // Verify interactions
        verify(modelMapper).map(userRequestDTO, User.class);
        verify(passwordEncoder).encode(userRequestDTO.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void shouldFindUserById() {
        // Arrange
        Long userId = 1L; // Example user ID
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserResponseDTO userResponseDTO = userService.findById(userId);

        // Assert
        assertEquals(userResponseDTO.getId(), userId);

        // Verify interactions
        verify(userRepository).findById(userId);
    }
}