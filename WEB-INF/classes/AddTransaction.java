package com.expensetracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import DatabaseConnection.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/AddTransaction")
public class AddTransaction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
            String Category = request.getParameter("Category");
            String user = request.getParameter("user");
            String ttype = request.getParameter("type");
            double amount = Double.parseDouble(request.getParameter("Amount"));
            String TransactionDate = request.getParameter("tDate");
            String Via=request.getParameter("Account");
            double newBalance=0.0;


        try {

            Connection connection = DBConnection.getConnection();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date tDate = dateFormat.parse(TransactionDate);
            java.sql.Date Date = new java.sql.Date(tDate.getTime());

            String query = "INSERT INTO transactions (userid, category, ttype, tdate, amount,via) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1,user);
                preparedStatement.setString(2, Category );
                preparedStatement.setString(3, ttype);
                preparedStatement.setDate(4, Date);
                preparedStatement.setDouble(5, amount);
                preparedStatement.setString(6,Via);

                preparedStatement.executeUpdate();

            }
            
            if(Via.equals("Wallet"))
            {
                String query1="SELECT BALANCE FROM users WHERE email=?";
                String query2="UPDATE users SET balance=? WHERE email=?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(query1))
            {
             preparedStatement.setString(1, user);   
             ResultSet resultSet = preparedStatement.executeQuery();
             resultSet.next();
             double balance=resultSet.getDouble("balance");
           
             if(ttype.equals("Income"))
               newBalance=balance+amount;
             else
               newBalance=balance-amount;  
            }
            catch(Exception e)
            {
                System.out.println(e);
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(query2))
            {
             preparedStatement.setDouble(1, newBalance);
             preparedStatement.setString(2, user);   
             preparedStatement.executeUpdate();

             HttpSession httpSession = request.getSession(true);
             httpSession.setAttribute("balance",newBalance);
  
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            }
            else
            {
            String query1="SELECT BALANCE FROM accounts WHERE accountid=?";
            String query2="UPDATE accounts SET balance=? WHERE accountid=?";
            int via=Integer.parseInt(Via);
            try(PreparedStatement preparedStatement = connection.prepareStatement(query1))
            {
             preparedStatement.setInt(1, via);   
             ResultSet resultSet = preparedStatement.executeQuery();
             resultSet.next();
             double balance=resultSet.getDouble("balance");
           
             if(ttype.equals("Income"))
               newBalance=balance+amount;
             else
               newBalance=balance-amount;  
            }
            catch(Exception e)
            {
                System.out.println(e);
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(query2))
            {
             preparedStatement.setDouble(1, newBalance);
             preparedStatement.setInt(2, via);   
             preparedStatement.executeUpdate();

  
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            }

        DBConnection.closeConnection(connection);

            response.sendRedirect("Dashboard.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
