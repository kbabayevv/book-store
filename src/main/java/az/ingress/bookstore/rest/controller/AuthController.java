package az.ingress.bookstore.rest.controller;


import az.ingress.bookstore.rest.dto.AuthenticationDTO;
import az.ingress.bookstore.rest.dto.JWTResponseDTO;
import az.ingress.bookstore.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authentication")
@AllArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping
    public ResponseEntity<JWTResponseDTO> authenticate(@Valid @RequestBody AuthenticationDTO dto) {
        authenticationManagerBuilder.getObject().authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        String token = tokenProvider.createToken(dto.getEmail());
        JWTResponseDTO jwtResponseDTO = new JWTResponseDTO();
        jwtResponseDTO.setAccessToken(token);
        return ResponseEntity.ok(jwtResponseDTO);
    }
}
