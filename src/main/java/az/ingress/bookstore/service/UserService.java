package az.ingress.bookstore.service;

import az.ingress.bookstore.entity.User;
import az.ingress.bookstore.repository.UserRepository;
import az.ingress.bookstore.rest.dto.request.UserRequestDTO;
import az.ingress.bookstore.rest.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        User user = mapper.map(userRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        return new UserResponseDTO(userRepository.save(user));

    }

    public UserResponseDTO findById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }
}
