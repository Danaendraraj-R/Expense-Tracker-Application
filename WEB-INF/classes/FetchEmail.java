import java.io.*;
import java.util.Date;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import java.util.Base64;


public class FetchEmail {

    public static void fetch(String pop3Host, String storeType, String user, String password) {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "pop3");
            properties.put("mail.pop3.host", pop3Host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");

            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("pop3s");
            store.connect(pop3Host, user, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            Message[] messages = emailFolder.getMessages();
            System.out.println("Number of messages: " + messages.length);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                writePart(message);
                String line = reader.readLine();
                if ("YES".equalsIgnoreCase(line)) {
                    message.writeTo(System.out);
                } else if ("QUIT".equalsIgnoreCase(line)) {
                    break;
                }
            }

            emailFolder.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "pop.gmail.com";
        String mailStoreType = "pop3";
        String username = "aproject487@gmail.com";
        String password = "tkxy fkcy xoju jnjd";

        fetch(host, mailStoreType, username, password);
    }

    private static void writePart(Part p) throws Exception {
        if (p instanceof Message) {
            writeEnvelope((Message) p);
        }

        System.out.println("----------------------------");
        System.out.println("CONTENT-TYPE: " + p.getContentType());

        if (p.isMimeType("text/plain")) {
            System.out.println("This is plain text");
            System.out.println("---------------------------");
            System.out.println((String) p.getContent());
        } else if (p.isMimeType("multipart/*")) {
            System.out.println("This is a Multipart");
            System.out.println("---------------------------");
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
                writePart(mp.getBodyPart(i));
            }
        } else if (p.isMimeType("message/rfc822")) {
            System.out.println("This is a Nested Message");
            System.out.println("---------------------------");
            writePart((Part) p.getContent());
        } else if (p.isMimeType("image/jpeg")) {
            System.out.println("--------> image/jpeg");
            Object o = p.getContent();

            InputStream x = (InputStream) o;
            byte[] bArray = new byte[x.available()];
            int result = x.read(bArray);

            FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
            f2.write(bArray);
        } else if (p.getContentType().contains("image/")) {
    System.out.println("--------> image/jpeg");
    Object o = p.getContent();

    try (InputStream inputStream = (InputStream) o) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        byte[] bArray = Base64.getDecoder().decode(byteArrayOutputStream.toByteArray());

        FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
        f2.write(bArray);
    }
} else {
            Object o = p.getContent();
            if (o instanceof String) {
                System.out.println("This is a string");
                System.out.println("---------------------------");
                System.out.println((String) o);
            } else if (o instanceof InputStream) {
                System.out.println("This is just an input stream");
                System.out.println("---------------------------");
                InputStream is = (InputStream) o;
                int c;
                while ((c = is.read()) != -1) {
                    System.out.write(c);
                }
            } else {
                System.out.println("This is an unknown type");
                System.out.println("---------------------------");
                System.out.println(o.toString());
            }
        }
    }

    private static void writeEnvelope(Message m) throws Exception {
        System.out.println("This is the message envelope");
        System.out.println("---------------------------");
        Address[] a;

        if ((a = m.getFrom()) != null) {
            for (Address address : a) {
                System.out.println("FROM: " + address.toString());
            }
        }

        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (Address address : a) {
                System.out.println("TO: " + address.toString());
            }
        }

        if (m.getSubject() != null) {
            System.out.println("SUBJECT: " + m.getSubject());
        }
    }
}
