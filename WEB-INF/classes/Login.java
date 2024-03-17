import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import DatabaseConnection.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Login")
@SuppressWarnings("unchecked")
public class Login extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String username = "username";
        String email = "email";
        double balance = 0.0;

        try {

            String user = req.getParameter("Email");
            String pass = req.getParameter("Password");

            PreparedStatement pstmt = null;

            Connection conn = DBConnection.getConnection();
            int allow = 0;

            String authenticationQuery = "SELECT username, email, balance FROM users WHERE email = ? AND password = crypt(?, password);";

            pstmt = conn.prepareStatement(authenticationQuery);
            pstmt.setString(1, user);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                email = rs.getString("email");
                balance = rs.getDouble("balance");

                allow++;

                username = rs.getString("username");

                HttpSession httpSession = req.getSession(true);
                httpSession.setAttribute("email", email);
                httpSession.setAttribute("username", username);
                httpSession.setAttribute("balance", balance);
                System.out.println("Login Successful");
                break;
            }

            rs.close();
            pstmt.close();

            if (allow == 1) {
                response.sendRedirect("Dashboard.jsp");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        DBConnection.closeConnection(conn);
        } 
        catch (Exception e) {
            out.println(e);
        }
    }
}
