package az.ingress.bookstore.service;

import az.ingress.bookstore.entity.User;
import az.ingress.bookstore.repository.UserRepository;
import az.ingress.bookstore.rest.dto.UserDTO;
import az.ingress.bookstore.rest.errors.EmailExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public void save(UserDTO userDTO) {
        // Check if the email is unique
        if (emailExists(userDTO.getEmail())) {
            throw new EmailExistsException("Email is already in use");
        }
        User user = mapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }

    public UserDTO getUserById(Long userId) {
        fetchUserIfExist(userId);
        return userRepository.findById(userId).map(UserDTO::new).orElseThrow();
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private void fetchUserIfExist(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    private boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
