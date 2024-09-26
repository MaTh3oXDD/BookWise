package org.example.Repository;


import org.example.ClassHibernate.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContaining(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByCategory_Id(Long categoryId);
    List<Book> findByTitle(String title);

}
