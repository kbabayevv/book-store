package az.ingress.bookstore.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationDTO {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
