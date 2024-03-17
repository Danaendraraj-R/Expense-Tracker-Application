import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.search.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Arrays;
import mail.ProcessMail;

public class EmailEventListener extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        // Start a background thread to continuously monitor the mailbox
        Thread emailMonitorThread = new Thread(() -> monitorEmails());
        emailMonitorThread.start();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // This servlet doesn't handle GET requests, you can customize it as needed
        response.getWriter().println("EmailListenerServlet is running.");
    }

   private void monitorEmails() {
    String host = "pop.gmail.com";
    String username = "aproject487@gmail.com";
    String password = "tkxy fkcy xoju jnjd";

    ProcessMail p1=new ProcessMail();

    Properties properties = new Properties();
    properties.setProperty("mail.store.protocol", "pop3");
    properties.setProperty("mail.pop3.host", host);
    properties.setProperty("mail.pop3.port", "995");
    properties.setProperty("mail.pop3.starttls.enable", "true");

    try {

        
        Session emailSession = Session.getDefaultInstance(properties);
        Store store = emailSession.getStore("pop3s");
        store.connect(host, username, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
            // Check for new emails every 5 seconds
            

        
            Message[] messages = inbox.getMessages();
            int len=messages.length;

        int i=0;


        while (true) {

        Folder Inbox = store.getFolder("INBOX");
        Inbox.open(Folder.READ_WRITE);

        Thread.sleep(5000);

        Message[] message = Inbox.getMessages();

            if (message.length > 0) {
                if(message.length > len)
                {
                System.out.println(i);
                len=message.length;
                Message latestMessage = message[message.length - 1];
                p1.TransformValues(latestMessage);
                
                i++;
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}
