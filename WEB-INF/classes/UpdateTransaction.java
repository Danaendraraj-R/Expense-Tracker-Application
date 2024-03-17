package com.expensetracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import DatabaseConnection.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateTransaction")
public class UpdateTransaction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = (String) request.getSession().getAttribute("email");
        int transactionId = Integer.parseInt(request.getParameter("transactionId"));
        double oldAmount = Double.parseDouble(request.getParameter("oldamount"));
        double newAmount = Double.parseDouble(request.getParameter("newAmount"));
        String via = request.getParameter("via");
        String Category = request.getParameter("Category");
        String transactionType = request.getParameter("type");

        System.out.println(user);
        System.out.println(transactionId);
        System.out.println(oldAmount);
        System.out.println(newAmount);
        System.out.println(Category);
        System.out.println(transactionType);
        System.out.println(via);
        

        try {
            Connection connection = DBConnection.getConnection();

            double difference = newAmount - oldAmount;

            String updateQuery = "UPDATE transactions SET amount=? WHERE transactionid=?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setDouble(1, newAmount);
                updateStatement.setInt(2, transactionId);
                updateStatement.executeUpdate();
            }

            if (transactionType.equals("Internal")) {
                handleInternalTransaction(request,user, via, difference,Category);
            } else {
                handleRegularTransaction(request,user, via, difference, transactionType);
            }

            DBConnection.closeConnection(connection);

            response.sendRedirect("Dashboard.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    private void handleInternalTransaction(HttpServletRequest request,String user, String via, double difference, String Category)
            throws Exception {
         Connection connection = DBConnection.getConnection();
        
        String accountQueryFrom = (via.equals("Wallet")) ? "UPDATE users SET balance=balance-? WHERE email=?"
            : "UPDATE accounts SET balance=balance-? WHERE accountid=?";
        
        String accountQueryTo = (via.equals("Wallet")) ? "UPDATE accounts SET balance=balance+? WHERE accountid=?":
        "UPDATE users SET balance=balance+? WHERE email=?";

        
            String[] categoryParts = Category.split("_to_");
            String sender = categoryParts[0];
            String receiver = categoryParts[1];

        try (PreparedStatement accountStatementFrom = connection.prepareStatement(accountQueryFrom)) {

            
                accountStatementFrom.setDouble(1, difference);
                if(via.equals("Wallet"))
                {
                     accountStatementFrom.setString(2, user);
                }
                else
                {
                accountStatementFrom.setInt(2, Integer.parseInt(sender) );
                }

                accountStatementFrom.executeUpdate();
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        try (PreparedStatement accountStatementTo = connection.prepareStatement(accountQueryTo)) {

                accountStatementTo.setDouble(1, difference);
                if(via.equals("Wallet"))
                {
                    accountStatementTo.setInt(2, Integer.parseInt(receiver) );
                }
                else
                {
                    accountStatementTo.setString(2, user);
                }
                accountStatementTo.executeUpdate();
            
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        
                HttpSession session = request.getSession(true);
                session.setAttribute("balance", getUpdatedBalance(connection, user));
                DBConnection.closeConnection(connection);
    }

    private void handleRegularTransaction(HttpServletRequest request, String user, String via, double difference, String type)
        throws Exception {
    Connection connection = DBConnection.getConnection();

    String accountQueryFrom = (via.equals("Wallet")) ? "UPDATE users SET balance=balance+? WHERE email=?"
            : "UPDATE accounts SET balance=balance+? WHERE accountid=?";

    try (PreparedStatement accountStatementFrom = connection.prepareStatement(accountQueryFrom);) {

        double adjustedDifference = (type.equals("Income")) ? difference : -difference;

        accountStatementFrom.setDouble(1, adjustedDifference);
        if(via.equals("Wallet"))
        {
            accountStatementFrom.setString(2, user);
        }
        else
        {
            accountStatementFrom.setInt(2, Integer.parseInt(via) );
        }
        accountStatementFrom.executeUpdate();

        if (via.equals("Wallet")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("balance", getUpdatedBalance(connection, user));
        }

    }
    DBConnection.closeConnection(connection);
}


    private double getUpdatedBalance(Connection connection, String user) throws Exception {
        String query = "SELECT balance FROM users WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("balance");
            }
        }
        throw new Exception("Error retrieving updated balance");
    }
}
