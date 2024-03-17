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


@WebServlet("/ITransaction")
public class ITransaction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            String From=request.getParameter("From");
            String To=request.getParameter("To");
            String Category =From+"_to_"+To;
            String user = request.getParameter("user");
            double amount = Double.parseDouble(request.getParameter("Amount"));
            String TransactionDate = request.getParameter("tDate");
            double newBalance1=0.0;
            double newBalance2=0.0;


        try {

            Connection connection = DBConnection.getConnection();

            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date tDate = dateFormat.parse(TransactionDate);
            java.sql.Date Date = new java.sql.Date(tDate.getTime());

            String query = "INSERT INTO transactions (userid, category, ttype, tdate, amount,via) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1,user);
                preparedStatement.setString(2, Category);
                preparedStatement.setString(3, "Internal");
                preparedStatement.setDate(4, Date);
                preparedStatement.setDouble(5, amount);
                preparedStatement.setString(6,From);

                preparedStatement.executeUpdate();

            }
            String query1="SELECT BALANCE FROM users WHERE email=?";
            String query2="UPDATE users SET balance=? WHERE email=?";
            String query3="SELECT BALANCE FROM accounts WHERE accountid=?";
            String query4="UPDATE accounts SET balance=? WHERE accountid=?";
            if(From.equals("Wallet"))
            {
            try(PreparedStatement preparedStatement = connection.prepareStatement(query1))
            {
             preparedStatement.setString(1, user);   
             ResultSet resultSet = preparedStatement.executeQuery();
             resultSet.next();
             double balance=resultSet.getDouble("balance");
             newBalance1=balance-amount;  
            }
            catch(Exception e)
            {
                System.out.println(e);
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(query2))
            {
             preparedStatement.setDouble(1, newBalance1);
             preparedStatement.setString(2, user);   
             preparedStatement.executeUpdate();

             HttpSession httpSession = request.getSession(true);
             httpSession.setAttribute("balance",newBalance1);
  
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement(query3))
            {
             int to=Integer.parseInt(To);   
             preparedStatement.setInt(1, to);   
             ResultSet resultSet1 = preparedStatement.executeQuery();
             resultSet1.next();
             double balance=resultSet1.getDouble("balance");
             newBalance2=balance+amount;  
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement(query4))
            {
             int to=Integer.parseInt(To); 
             preparedStatement.setDouble(1, newBalance2);
             preparedStatement.setInt(2, to);   
             preparedStatement.executeUpdate();

  
            }
            catch(Exception e)
            {
                System.out.println(e);
            }

            }
            else
            {

            try(PreparedStatement preparedStatement = connection.prepareStatement(query3))
            {
             int from=Integer.parseInt(From); 
             preparedStatement.setInt(1,from);   
             ResultSet resultSet2 = preparedStatement.executeQuery();
             resultSet2.next();
             double balance=resultSet2.getDouble("balance");
             newBalance2=balance-amount;  
            }
            catch(Exception e)
            {
                System.out.println("Error in query 3");
                System.out.println(e);
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(query4))
            {
              int from=Integer.parseInt(From); 
             preparedStatement.setDouble(1, newBalance2);
             preparedStatement.setInt(2, from);   
             preparedStatement.executeUpdate();
  
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement(query1))
            {
             preparedStatement.setString(1, user);   
             ResultSet resultSet3 = preparedStatement.executeQuery();
             resultSet3.next();
             double balance=resultSet3.getDouble("balance");
             newBalance1=balance+amount;  
            }
            catch(Exception e)
            {
                System.out.println("Error in query 1");
                System.out.println(e);
            }

            try(PreparedStatement preparedStatement = connection.prepareStatement(query2))
            {
             preparedStatement.setDouble(1, newBalance1);
             preparedStatement.setString(2, user);   
             preparedStatement.executeUpdate();

             HttpSession httpSession = request.getSession(true);
             httpSession.setAttribute("balance",newBalance1);
  
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
