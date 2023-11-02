package az.ingress.bookstore.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@ToString
public class BookDTO {
    Long id;

    @NotEmpty
    @Size(min = 1, max = 255)
    String name;

    @NotEmpty
    Long authorId;

    @NotEmpty
    List<Long> readerIds;
}
