package az.ingress.bookstore.service;

import az.ingress.bookstore.entity.Book;
import az.ingress.bookstore.repository.BookRepository;
import az.ingress.bookstore.rest.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;


    public void save(BookDTO bookDTO) {
        Book book = mapper.map(bookDTO, Book.class);
        bookRepository.save(book);
    }

    public BookDTO getBookById(Long bookId) {
        fetchBookIfExist(bookId);
        Book book = bookRepository.findById(bookId).orElseThrow();
        return mapper.map(book, BookDTO.class);
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> mapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }


    public void updateBook(Long bookId, BookDTO updatedBookDTO) {
        fetchBookIfExist(bookId);
        Book existingBook = bookRepository.findById(bookId).orElseThrow();
        existingBook.setName(updatedBookDTO.getName());
        bookRepository.save(existingBook);
    }

    @Transactional
    public void deleteBook(Long bookId) {
        fetchBookIfExist(bookId);
        bookRepository.deleteById(bookId);
    }

    private void fetchBookIfExist(Long bookId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));
    }

}
