package az.ingress.bookstore.rest.controller;

import az.ingress.bookstore.rest.dto.request.BookReaderRequestDTO;
import az.ingress.bookstore.rest.dto.request.BookRequestDTO;
import az.ingress.bookstore.rest.dto.response.BookResponseDTO;
import az.ingress.bookstore.rest.dto.response.UserResponseDTO;
import az.ingress.bookstore.service.BookService;
import az.ingress.bookstore.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> save(@Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.save(dto));
    }

    @PostMapping("/reader")
    public ResponseEntity<Void> addReader(@Valid @RequestBody BookReaderRequestDTO dto) {
        bookService.addReaderForBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/currently-reading")
    public ResponseEntity<List<BookResponseDTO>> readerBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.readerBooks());
    }

    @GetMapping("/{id}/readers")
    public ResponseEntity<List<UserResponseDTO>> readerBooks(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.findReadersById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
