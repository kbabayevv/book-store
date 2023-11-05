package az.ingress.bookstore.rest.dto.response;

import az.ingress.bookstore.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@ToString
public class BookResponseDTO {
    Long id;

    String name;

    UserResponseDTO author;

    public static BookResponseDTO toDTO(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .author(new UserResponseDTO(book.getAuthor()))
                .build();
    }
}
