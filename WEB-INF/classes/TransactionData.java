package com.expensetracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import DatabaseConnection.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/TransactionData")
public class TransactionData extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        try{

            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM transactions WHERE userid=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                HttpSession session = request.getSession(true);
                preparedStatement.setString(1,(String)session.getAttribute("email"));
                ResultSet resultSet = preparedStatement.executeQuery();

                List<Transactions> transactions = new ArrayList<>();

                while (resultSet.next()) {
                    Transactions transaction = new Transactions();
                    transaction.setType(resultSet.getString("ttype"));
                    transaction.setCategory(resultSet.getString("category"));
                    transaction.setUserId(resultSet.getString("userid"));
                    transaction.setAmount(resultSet.getDouble("amount"));
                    transaction.setDate(resultSet.getDate("tdate"));
                    transaction.setVia(resultSet.getString("via"));
                    transaction.setTransactionId(resultSet.getInt("transactionid"));
                    

                    transactions.add(transaction);
                }

                
                StringBuilder jsonBuilder = new StringBuilder("[");
                for (Transactions transaction : transactions) {
                    jsonBuilder.append(transaction.toJsonString()).append(",");
                }
                if (transactions.size() > 0) {
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

    private static class Transactions {

        private String type;
        private String category;
        private String userid;
        private java.sql.Date date;
        private double amount;
        private String Via;
        private int transactionid;

    public String getUserId() {
        return userid;
    }

    public void setUserId(String userId) {
        this.userid = userId;
    }
    
    public int getTransactionId() {
        return transactionid;
    }

    public void setTransactionId(int transactionid) {
        this.transactionid = transactionid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String Category) {
        this.category = Category;
    }
    public String getVia() {
        return Via;
    }

    public void setVia(String Via) {
        this.Via = Via;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

        public String toJsonString() {
            return String.format(
                    "{\"UserId\":\"%s\",\"Category\":\"%s\",\"Type\":\"%s\",\"Amount\":%f,\"Date\":\"%s\",\"Via\":\"%s\",\"TransactionId\":%d}",
                    userid, category, type, amount, date, Via, transactionid);
        }
    }
}
