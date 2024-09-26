package org.example.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
public class UserAddController {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bookwise";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone) {

        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        String query = "INSERT INTO users (first_name, last_name, email, phone_number, registration_date) VALUES (?, ?, ?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, timeStamp);

            int rowsInserted = pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            if (rowsInserted > 0) {
                return ResponseEntity.ok("Użytkownik został dodany pomyślnie!");
            } else {
                return ResponseEntity.status(500).body("Wystąpił błąd podczas dodawania użytkownika.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Wystąpił błąd podczas dodawania użytkownika.");
        }
    }
}
