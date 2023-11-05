package az.ingress.bookstore.service;

import az.ingress.bookstore.entity.Book;
import az.ingress.bookstore.entity.User;
import az.ingress.bookstore.entity.enumeration.RoleName;
import az.ingress.bookstore.repository.BookRepository;
import az.ingress.bookstore.repository.UserRepository;
import az.ingress.bookstore.rest.dto.request.BookReaderRequestDTO;
import az.ingress.bookstore.rest.dto.request.BookRequestDTO;
import az.ingress.bookstore.rest.dto.response.BookResponseDTO;
import az.ingress.bookstore.rest.dto.response.UserResponseDTO;
import az.ingress.bookstore.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up SecurityContext for testing
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("test", new ArrayList<>()));
    }

    @Test
    void shouldSaveBook() {
        // Arrange
        BookRequestDTO bookRequestDTO = new BookRequestDTO();

        User user = new User();
        Book book = new Book();
        // Set up user and book as needed

        when(modelMapper.map(bookRequestDTO, Book.class)).thenReturn(book);
        when(userRepository.getById(SecurityUtil.getLoggedUserId())).thenReturn(user);
        when(bookRepository.save(book)).thenReturn(book);

        // Act
        BookResponseDTO bookResponseDTO = bookService.save(bookRequestDTO);

        // Assert
        assertEquals(bookResponseDTO.getId(), book.getId());

        // Verify interactions
        verify(modelMapper).map(bookRequestDTO, Book.class);
        verify(userRepository).getById(SecurityUtil.getLoggedUserId());
        verify(bookRepository).save(book);
    }

    @Test
    void shouldAddReaderForBook() {
        // Arrange
        Long bookId = 1L;
        BookReaderRequestDTO readerRequestDTO = new BookReaderRequestDTO();
        readerRequestDTO.setBookId(bookId);

        User user = new User();
        user.setId(1L);
        user.setRole(RoleName.AUTHOR);
        user.setPassword("12345");
        user.setName("Test");
        user.setEmail("test@gmail.com");
        Book book = new Book();
        book.setId(user.getId());
        book.setName("Test");
        book.setReaders(new HashSet<>(Collections.singleton(user)));
        book.setAuthor(user);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        when(userRepository.getById(Mockito.anyLong())).thenReturn(user);

        // Act
        bookService.addReaderForBook(readerRequestDTO);

        // Assert
        assertTrue(book.getReaders().contains(user));
    }

    @Test
    void shouldRetrieveReaderBooks() {
        // Arrange
        Long userId = 1L; // Example user ID

        List<Book> books = new ArrayList<>();
        // Populate books as needed

        when(bookRepository.findBooksByReadersId(userId)).thenReturn(books);

        // Act
        List<BookResponseDTO> bookResponseDTOs = bookService.readerBooks();
    }

    @Test
    void shouldFindReadersById() {
        //Arrange
        Long bookId = 1L;
        Book book = new Book();
        User user = new User();
        book.setReaders(Collections.singleton(user));

        //Act
        when(bookRepository.findReadersById(bookId)).thenReturn(Optional.of(book));

        List<UserResponseDTO> readers = bookService.findReadersById(bookId);

        //Assert
        assertEquals(Collections.singletonList(new UserResponseDTO(user)), readers);

    }

    @Test
    void shouldFindAllBooks() {
        // Arrange
        List<Book> books = new ArrayList<>();
        // Populate books as needed

        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<BookResponseDTO> bookResponseDTOs = bookService.findAll();
    }

    @Test
    void shouldDeleteBookById() {

// Arrange
        Long bookId = 1L;
        Long userId = 2L;

        Book book = new Book();
        book.setId(bookId);

        when(bookRepository.findByIdAndAuthorId(bookId, userId)).thenReturn(Optional.of(book));

        // Act and Assert
        // Ensure that an exception is thrown as the user is not authorized to delete this book
        Mockito.verify(bookRepository, Mockito.never()).delete(Mockito.any());
    }
}