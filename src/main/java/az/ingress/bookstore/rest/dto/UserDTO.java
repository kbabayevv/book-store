package az.ingress.bookstore.rest.dto;

import az.ingress.bookstore.entity.User;
import az.ingress.bookstore.entity.enumertion.RoleName;
import az.ingress.bookstore.validation.UniqueField;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    Long id;

    @NotEmpty
    @Size(min = 1, max = 50)
    String name;

    @NotEmpty
    @Size(min = 1, max = 50)
    @Email
    @UniqueField(entityClass = User.class, fieldName = "email")
    String email;

    @NotEmpty
    @Size(min = 6, max = 100)
    private String password;

    @NotEmpty
    RoleName role; // Author or Student

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
