package org.example.Serwlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getBookData")
public class BookTableDataServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<table border='1'>");
        out.println("<thead><tr>"
                + "<th>ID</th>"
                + "<th>Title</th>"
                + "<th>Author</th>"
                + "<th>Published Date</th>"
                + "<th>Category ID</th>"
                + "<th>Total Copies</th>"
                + "<th>Available Copies</th>"
                + "<th>Description</th>"
                + "</tr></thead>");
        out.println("<tbody>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookwise", "root", "");

            String query = "SELECT id, title, author, published_date, category_id, total_copies, available_copies, description FROM books";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("author") + "</td>");
                out.println("<td>" + rs.getDate("published_date") + "</td>");
                out.println("<td>" + rs.getInt("category_id") + "</td>");
                out.println("<td>" + rs.getInt("total_copies") + "</td>");
                out.println("<td>" + rs.getInt("available_copies") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("</tr>");
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        }

        out.println("</tbody>");
        out.println("</table>");
    }
}
