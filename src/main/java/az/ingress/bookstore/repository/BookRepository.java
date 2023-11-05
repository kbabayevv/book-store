package az.ingress.bookstore.repository;

import az.ingress.bookstore.entity.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findBooksByReadersId(Long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "readers")
    Optional<Book> findReadersById(Long id);

    Optional<Book> findByIdAndAuthorId(Long id, Long authorId);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "readers")
    Optional<Book> findById(Long id);
}
