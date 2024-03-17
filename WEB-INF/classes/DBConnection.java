package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection con = null;

    public static Connection getConnection() {
       
        String url = "jdbc:postgresql://localhost:5432/expense";
        String user = "postgres";
        String pass = "Rajdr039*";
        try {
         if (con == null || con.isClosed()) {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, pass);
            return con;
         }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
            
        return con;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
