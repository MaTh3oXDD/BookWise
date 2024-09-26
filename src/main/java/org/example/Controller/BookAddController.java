package org.example.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Map;

@RestController
public class BookAddController {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bookwise";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestParam Map<String, String> bookData) {
        String title = bookData.get("title");
        String author = bookData.get("author");
        String publishedDate = bookData.get("published_date");
        int categoryId = Integer.parseInt(bookData.get("category_id"));
        int totalCopies = Integer.parseInt(bookData.get("total_copies"));
        int availableCopies = Integer.parseInt(bookData.get("available_copies"));
        String description = bookData.get("description");

        String query = "INSERT INTO books (title, author, published_date, category_id, total_copies, available_copies, description) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, publishedDate);
            pstmt.setInt(4, categoryId);
            pstmt.setInt(5, totalCopies);
            pstmt.setInt(6, availableCopies);
            pstmt.setString(7, description);

            int rowsInserted = pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            if (rowsInserted > 0) {
                return ResponseEntity.ok("Książka została dodana pomyślnie!");
            } else {
                return ResponseEntity.status(500).body("Wystąpił błąd podczas dodawania książki.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Wystąpił błąd podczas dodawania książki.");
        }
    }
}
