import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendAttachmentInEmail {
	public static void main(String[] args) {
		// Recipient's email ID needs to be mentioned.
		Scanner sc = new Scanner(System.in);

		Map<String, ArrayList<String>> hashm = new HashMap<String, ArrayList<String>>();
		// HashMap hashm = new HashMap();
		ArrayList<String> al = new ArrayList<String>();
		al.add("siddhishah007@gmail.com");
		al.add("sss3301@rit.edu");
		hashm.put("us@gmail.com", al);
		ArrayList<String> al1 = new ArrayList<String>();
		al1.add("siddhishah007@gmail.com");
		al1.add("pss3096@rit.edu");
		hashm.put("parin@gmail.com", al);
		// Sender's email ID needs to be mentioned

		System.out.println("Enter Mail From");
		String from = sc.next();

		System.out.println("Enter RCPT_TO");
		String to = sc.next();

		final String username = "fcnproj@gmail.com";// change accordingly
		final String password = "qwerasdz";// change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			ArrayList<String> value = null;
			for (Map.Entry<String, ArrayList<String>> entry : hashm.entrySet()) {
				String key = entry.getKey();
				if (key.equalsIgnoreCase(to)) {
					value = entry.getValue();
					// for(String aString : value){
					// System.out.println("key : " + key + " value : " +
					// aString);
					// }

				}

			}
			InternetAddress[] addressTo = new InternetAddress[value.size()];
			for (int i = 0; i < value.size(); i++) {
				addressTo[i] = new InternetAddress(value.get(i));
			}
			// msg.setRecipients(RecipientType.TO, addressTo); // Set To: header
			// field of the header.

			message.setRecipients(Message.RecipientType.TO, addressTo);

			// Set Subject: header field

			System.out.println("Subject: ");
			String subject = sc.next();
			message.setSubject(subject);

			// Now set the actual message

			System.out.println("Text: ");
			String text = sc.next();
			message.setText(text);

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}