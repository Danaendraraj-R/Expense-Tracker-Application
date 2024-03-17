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


@WebServlet("/AddAccount")
public class AddAccount extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Connection conn = DBConnection.getConnection();
            System.out.println("connection successful");

            PreparedStatement st = conn.prepareStatement("insert into accounts(userid,accounttype,bankname) values( ?, ?, ?) ");

            st.setString(1, request.getParameter("Email"));
            st.setString(2, request.getParameter("AccountType"));     
            st.setString(3, request.getParameter("AccountName"));
            st.executeUpdate();


            st.close();
            DBConnection.closeConnection(conn);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
