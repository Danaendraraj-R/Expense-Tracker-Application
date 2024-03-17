package com.expensetracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DatabaseConnection.DBConnection;

@WebServlet("/GetCategories")
public class GetCategories extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        

        try {

            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM categories WHERE users = 'All' OR users = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                HttpSession session = request.getSession(true);
                preparedStatement.setString(1,(String)session.getAttribute("email"));
                ResultSet resultSet = preparedStatement.executeQuery();

                List<Categories> category = new ArrayList<>();

                while (resultSet.next()) {
                    Categories ct = new Categories();
                    ct.setCategory(resultSet.getString("category"));
                    ct.setUser(resultSet.getString("users"));
                    category.add(ct);
                }

                StringBuilder jsonBuilder = new StringBuilder("[");
                for (Categories cat : category) {
                    jsonBuilder.append(cat.toJsonString()).append(",");
                }
                if (category.size() > 0) {
                    jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); 
                }
                jsonBuilder.append("]");

                try (PrintWriter out = response.getWriter()) {
                    out.print(jsonBuilder.toString());
                    out.flush();
                }
            }
            DBConnection.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Categories {
        private String Category;
        private String user;

        public Categories() {
       
        }

        public String getCategory() {
            return Category;
        }

        public void setCategory(String Category) {
            this.Category = Category;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String User) {
            this.user = User;
        }

        public String toJsonString() {
            return String.format("{\"Category\":\"%s\",\"User\":\"%s\"}",Category, user);
        }
    }
}
