package org.example;

import org.example.ClassHibernate.Book;
import org.example.ClassHibernate.Category;
import org.example.Repository.BookRepository;
import org.example.Repository.CategoryRepository; // Dodane repozytorium dla kategorii
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {

        Category category = new Category("Fantasy", "Fantasy");
        categoryRepository.save(category);

        Book book1 = new Book(
            "To Kill a Mockingbird",
            "Harper Lee",
            LocalDate.of(1960, 7, 11).toString(),
            category,
            10,
            8,
            "A novel about racial injustice in the Deep South."
        );


        bookRepository.save(book1);

        List<Book> books = bookRepository.findByTitle("To Kill a Mockingbird");
        if (!books.isEmpty()) {
            Book foundBook = books.get(0);
            System.out.println("Znaleziono książkę: " + foundBook.getTitle() + ", autor: " + foundBook.getAuthor());
        } else {
            System.out.println("Nie znaleziono książki o podanym tytule.");
        }
        System.out.println("Wszystkie książki w bazie danych:");
        bookRepository.findAll().forEach(book -> System.out.println(book.getTitle() + " by " + book.getAuthor()));


        bookRepository.delete(book1);
        System.out.println("Książka została usunięta.");
    }
}
