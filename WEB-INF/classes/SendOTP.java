import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;

@WebServlet("/SendOTP")
public class SendOTP extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String to = request.getParameter("email"); 

        final String from = "aproject487@gmail.com"; 
        final String password = "tkxy fkcy xoju jnjd";


        String otp = generateOTP();
        Cookie cookie = new Cookie("otp", otp);

        cookie.setMaxAge(300); 
        cookie.setPath("/");

        response.addCookie(cookie);


        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); 
        properties.put("mail.smtp.port", "587"); 

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });


        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("OTP verification for account creation- Reg");
            message.setText("Your OTP for the account creation is"+ otp );
            Transport.send(message);

        } catch (MessagingException e) {
            throw new ServletException("Could not send email.", e);
        }
    }
        private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
}
