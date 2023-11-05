package az.ingress.bookstore.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {
    private String title;
    private String status;
    private Map<String, String> validationErrors;
}
