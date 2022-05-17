package Services;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class SendMailUtil {
    private static Action action;
    private static GlobalVariables globalVariables;

    public SendMailUtil() {

    }

    public void sendMail(String sender, String senderPassword, String recepient) throws Exception {
        Properties props=new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, senderPassword);
                    }
                });

        Message msg = new MimeMessage(session);
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            msg.setSubject(" Orbitz Automation Report : "+dtf.format(LocalDateTime.now()));

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Please find the Orbitz Automation report in attachments"+"\n\nNo need to reply.");

            MimeBodyPart attachmentBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource(System.getProperty("user.dir") + "\\test-output\\extentReports\\extentreport.html");
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("ExtentReport.html");
            multipart.addBodyPart(textBodyPart);  // add the text part
            multipart.addBodyPart(attachmentBodyPart); // add the attachment part
            msg.setContent(multipart);

            Transport.send(msg);

            System.out.println("Email Sent Successfully !!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}