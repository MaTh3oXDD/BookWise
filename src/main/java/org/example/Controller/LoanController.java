package org.example.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
public class LoanController {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bookwise";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @PostMapping("/addLoan")
    public ResponseEntity<String> addLoan(@RequestParam int user_id, @RequestParam int book_id) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            String loanDate = sdf.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 14);
            String dueDate = sdf.format(calendar.getTime());

            String query = "INSERT INTO loans (user_id, book_id, loan_date, due_date, status) VALUES (?, ?, ?, ?, ?)";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, user_id);
            pstmt.setInt(2, book_id);
            pstmt.setString(3, loanDate);
            pstmt.setString(4, dueDate);
            pstmt.setString(5, "Wypożyczona");

            int rowsInserted = pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            if (rowsInserted > 0) {
                return ResponseEntity.ok("Wypożyczenie zostało dodane pomyślnie!");
            } else {
                return ResponseEntity.status(500).body("Wystąpił błąd podczas dodawania wypożyczenia.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Wystąpił błąd podczas dodawania wypożyczenia.");
        }
    }
}
