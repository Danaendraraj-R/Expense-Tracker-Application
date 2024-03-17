import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try{
        HttpSession session = request.getSession(false);
        if (session != null) {

            session.setAttribute("username", null);
            session.setAttribute("email", null);
            
            session.invalidate();
        }

        response.sendRedirect("Login.jsp");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
