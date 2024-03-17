package com.expensetracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import DatabaseConnection.DBConnection;

import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddCategory")
public class AddCategory extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Connection conn = DBConnection.getConnection();
            System.out.println("connection successful");
            String query="insert into categories(CATEGORY,USERS) values(?, ?)";

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, request.getParameter("Category"));
            st.setString(2, request.getParameter("Email"));
            st.executeUpdate();


            st.close();
            DBConnection.closeConnection(conn);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
