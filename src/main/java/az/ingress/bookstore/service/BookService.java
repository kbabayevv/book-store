package az.ingress.bookstore.service;

import az.ingress.bookstore.entity.Book;
import az.ingress.bookstore.entity.User;
import az.ingress.bookstore.repository.BookRepository;
import az.ingress.bookstore.repository.UserRepository;
import az.ingress.bookstore.rest.dto.request.BookReaderRequestDTO;
import az.ingress.bookstore.rest.dto.request.BookRequestDTO;
import az.ingress.bookstore.rest.dto.response.BookResponseDTO;
import az.ingress.bookstore.rest.dto.response.UserResponseDTO;
import az.ingress.bookstore.util.SecurityUtil;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public BookResponseDTO save(BookRequestDTO bookRequestDTO) {
        Book book = mapper.map(bookRequestDTO, Book.class);
        book.setAuthor(getUserById());
        bookRepository.save(book);
        return BookResponseDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .build();
    }

    public void addReaderForBook(BookReaderRequestDTO readerRequestDTO) {
        Book book = bookRepository.findById(readerRequestDTO.getBookId())
                .orElseThrow(EntityNotFoundException::new);
        Set<User> readers = book.getReaders();
        readers.add(getUserById());
        bookRepository.save(book);
    }


    public List<BookResponseDTO> readerBooks() {
        Long studentId = SecurityUtil.getLoggedUserId();
        return bookRepository.findBooksByReadersId(studentId).stream()
                .map(BookResponseDTO::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserResponseDTO> findReadersById(Long id) {
        Book book = bookRepository.findReadersById(id).orElseThrow(EntityNotFoundException::new);
        return book.getReaders().stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }


    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(BookResponseDTO::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteBook(Long bookId, Long authorId) {
        // Check if the book belongs to the author
        List<Book> books = bookRepository.findBookByAuthorId(authorId);
        Book bookToDelete = books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Book not found or does not belong to the author."));

        bookRepository.deleteById(bookToDelete.getId());
    }

    private User getUserById() {
        return userRepository.getById(SecurityUtil.getLoggedUserId());
    }
}
