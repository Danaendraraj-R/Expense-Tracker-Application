import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import DatabaseConnection.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("connection successful");

            String username = request.getParameter("Username");
            String email = request.getParameter("Email");
            String password = request.getParameter("Password");


            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO users (USERNAME, EMAIL, PASSWORD) VALUES (?, ?, crypt(?, gen_salt('bf')))");
            st.setString(1, username);
            st.setString(2, email);
            st.setString(3, password);
            st.executeUpdate();

            st.close();
            DBConnection.closeConnection(conn);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        
        }
    }
}
