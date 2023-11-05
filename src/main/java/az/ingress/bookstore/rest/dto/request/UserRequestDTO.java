package az.ingress.bookstore.rest.dto.request;

import az.ingress.bookstore.entity.User;
import az.ingress.bookstore.entity.enumeration.RoleName;
import az.ingress.bookstore.validation.UniqueField;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDTO {
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
    String password;

    @NotNull
    RoleName role; // Author or Student

    public UserRequestDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
