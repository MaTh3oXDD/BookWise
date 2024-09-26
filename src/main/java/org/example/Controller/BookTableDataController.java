package org.example.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
public class BookTableDataController {

    @GetMapping("/getBookTable")
    public String getBookTable() {
        StringBuilder result = new StringBuilder();

        result.append("<table border='1'>")
                .append("<thead><tr>")
                .append("<th>ID</th>")
                .append("<th>Title</th>")
                .append("<th>Author</th>")
                .append("<th>Published Date</th>")
                .append("<th>Category ID</th>")
                .append("<th>Total Copies</th>")
                .append("<th>Available Copies</th>")
                .append("<th>Description</th>")
                .append("</tr></thead>")
                .append("<tbody>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookwise", "root", "");

            String query = "SELECT id, title, author, published_date, category_id, total_copies, available_copies, description FROM books";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                result.append("<tr>")
                        .append("<td>").append(rs.getInt("id")).append("</td>")
                        .append("<td>").append(rs.getString("title")).append("</td>")
                        .append("<td>").append(rs.getString("author")).append("</td>")
                        .append("<td>").append(rs.getDate("published_date")).append("</td>")
                        .append("<td>").append(rs.getInt("category_id")).append("</td>")
                        .append("<td>").append(rs.getInt("total_copies")).append("</td>")
                        .append("<td>").append(rs.getInt("available_copies")).append("</td>")
                        .append("<td>").append(rs.getString("description")).append("</td>")
                        .append("</tr>");
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            result.append("<tr><td colspan='8'>Error retrieving data</td></tr>");
        }

        result.append("</tbody></table>");
        return result.toString();
    }
}
