package az.ingress.bookstore.security;

import az.ingress.bookstore.entity.User;
import az.ingress.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) throw new UsernameNotFoundException("User not found");
        User user = userOptional.get();

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>());
    }

}
