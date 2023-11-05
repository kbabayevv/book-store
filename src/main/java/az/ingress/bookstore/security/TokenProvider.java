package az.ingress.bookstore.security;

import az.ingress.bookstore.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class TokenProvider {
    private final JwtParser jwtParser;

    private final Key key;

    private final UserRepository userRepository;

    @Value("${security.jwt.validity-in-seconds}")
    private long tokenValidityInSeconds;

    public TokenProvider(
            @Value("${security.jwt.base64-secret-key}") String base64Secret,
            UserRepository userRepository) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.userRepository = userRepository;
    }

    public String createToken(String username) {

        long now = (new Date()).getTime();
        Date validity = new Date(now + (this.tokenValidityInSeconds * 1000));

        return Jwts
                .builder()
                .setSubject(username)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Optional<az.ingress.bookstore.entity.User> optionalUser = userRepository.findByEmail(claims.getSubject());

        if (optionalUser.isEmpty()) {
            return null;
        }

        az.ingress.bookstore.entity.User user = optionalUser.get();

        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        User principal = new User(user.getEmail(), "", grantedAuthorities);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
        authenticationToken.setDetails(user.getId());
        return authenticationToken;
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException ignored) {
        }
        return false;
    }

}
