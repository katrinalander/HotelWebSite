package core;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailNotification extends Thread {
	
	String mailTo;
	String subject;
	String text;

	
	public MailNotification(String mailTo, String subject, String text) {
		this.mailTo = mailTo;
		this.subject = subject;
		this.text = text;
	}
	
	public void run(){
		final String username = "katrina.lander@gmail.com";
		final String password = "fialka19631";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Katrina.Lander@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mailTo));
			message.setSubject(subject);
//			message.setText(text);
// --------------Message with attachments------------------------------
			MimeMultipart multipart = new MimeMultipart("related");

			// first part  (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(text, "text/html");

			// add it
			multipart.addBodyPart(messageBodyPart);
				        
			// second part (the image)
			messageBodyPart = new MimeBodyPart();
			DataSource fds = new FileDataSource
			("C:\\pic-hotel\\logo.png");
			messageBodyPart.setDataHandler(new DataHandler(fds));
			messageBodyPart.setHeader("Content-ID","<image>");

			// add it
			multipart.addBodyPart(messageBodyPart);

			// put everything together
			message.setContent(multipart );
			//---------------------------------------------------------------------
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
