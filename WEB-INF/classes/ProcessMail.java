package mail;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class ProcessMail {

public void TransformValues(Message message) throws Exception
{
try {
        System.out.println("Subject: " + message.getSubject());
        System.out.println("From: " + Arrays.toString(message.getFrom()));
        System.out.println("Sent Date: " + message.getSentDate());
        printTextContent(message);

        System.out.println("-----------------------------------");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

     private static void printTextContent(Part part) throws MessagingException, IOException {
        Object content = part.getContent();

        if (part.isMimeType("text/plain")) {
            System.out.println("Text Content: " + content);
        } else if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            int count = multipart.getCount();

            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                printTextContent(bodyPart);
            }
        }
    }


}
