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

@WebServlet("/BankData")
public class BankData extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        try {

            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM accounts WHERE userid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                HttpSession session = request.getSession(true);
                preparedStatement.setString(1, (String) session.getAttribute("email"));
                ResultSet resultSet = preparedStatement.executeQuery();

                List<Accounts> accounts = new ArrayList<>();

                while (resultSet.next()) {
                    Accounts acc = new Accounts();
                    acc.setAccountId(resultSet.getInt("accountid"));
                    acc.setBalance(resultSet.getDouble("balance"));
                    acc.setAccountType(resultSet.getString("accounttype"));
                    acc.setBankName(resultSet.getString("bankname"));
                    acc.setUserId(resultSet.getString("userid"));
                    accounts.add(acc);
                }

                StringBuilder jsonBuilder = new StringBuilder("[");
                for (Accounts acc : accounts) {
                    jsonBuilder.append(acc.toJsonString()).append(",");
                }
                if (accounts.size() > 0) {
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

    private static class Accounts {
        private int accountId;
        private double balance;
        private String accountType;
        private String bankName;
        private String userId;

        public Accounts() {
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String toJsonString() {
            return String.format("{\"AccountId\":%d,\"Balance\":%.2f,\"AccountType\":\"%s\",\"BankName\":\"%s\",\"UserId\":\"%s\"}",
                    accountId, balance, accountType, bankName, userId);
        }
    }
}
