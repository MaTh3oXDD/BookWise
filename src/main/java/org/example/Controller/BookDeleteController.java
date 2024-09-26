package org.example.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@RestController
public class BookDeleteController {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bookwise";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @PostMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam int bookId) {

        String query = "DELETE FROM books WHERE id = ?";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query);


            pstmt.setInt(1, bookId);


            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            conn.close();


            if (rowsAffected > 0) {
                return ResponseEntity.ok("Książka o ID " + bookId + " została usunięta.");
            } else {
                return ResponseEntity.status(404).body("Książka o ID " + bookId + " nie została znaleziona.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Wystąpił błąd podczas usuwania książki.");
        }
    }
}
