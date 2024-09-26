package org.example.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
public class UserTableDataController {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bookwise";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @GetMapping("/getTableData")
    public String getTableData() {
        StringBuilder result = new StringBuilder();

        result.append("<table>");
        result.append("<thead><tr><th>ID</th><th>First Name</th><th>Last Name</th><th>Email</th></tr></thead>");
        result.append("<tbody>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            String query = "SELECT id, first_name, last_name, email FROM users";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.append("<tr>");
                result.append("<td>").append(rs.getInt("id")).append("</td>");
                result.append("<td>").append(rs.getString("first_name")).append("</td>");
                result.append("<td>").append(rs.getString("last_name")).append("</td>");
                result.append("<td>").append(rs.getString("email")).append("</td>");
                result.append("</tr>");
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "<p>Error occurred while fetching data!</p>";
        }

        result.append("</tbody>");
        result.append("</table>");

        return result.toString();
    }
}
