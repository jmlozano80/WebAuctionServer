package email_sender;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*import android.util.Log;*/

public class CopyOfGmailSender extends Authenticator {
	private final static String HOST = "smtp.gmail.com";
	private static String username;
	private static String password;
	private static Session session;
	private static Transport transport;



	public CopyOfGmailSender() {
		System.out.println("Constructor()");
		username = "joselozano0066";
		password ="hackney32";

		Properties properties = new Properties();
		/*properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.quitwait", "false");
		properties.setProperty("mail.smtp.host", HOST);*/
		properties.put("mail.smtp.user", username);
		properties.put("mail.smtp.host", HOST);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.debug", "true");
		properties.put("mail.smtp.auth", "true");
		/*properties.put("mail.smtp.password", password);*/
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");

		/*
		 * JSSEProvider auth = new JSSEProvider(); Session session =
		 * Session.getInstance(properties, auth);
		 */
		session = Session.getInstance(properties,
				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		session.setDebug(true);
	}

	public synchronized static void sendMail(String subject, String body,
			String sender, String recipients) {
		try {
			System.out.println("sendMail()");
			MimeMessage message = new MimeMessage(session);
			DataHandler handler = new DataHandler(new ByteArrayDataSource(
					body.getBytes(), "text/plain"));
			message.setSender(new InternetAddress(sender));
			message.setSubject(subject);
			System.out.println("Subject set.");
			message.setDataHandler(handler);
			System.out.println("Data handler set.");
			if (recipients.indexOf(',') > 0) {
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(recipients));
			} else {
				message.setRecipient(Message.RecipientType.TO,
						new InternetAddress(recipients));
			}
			System.out.println("GmailSender" +
					"Attempting to send message to - " + recipients
							+ " With message - subj: " + message.getSubject()
							+ " content: " + message.getContent().toString());
			if (message != null) {
				System.out.println("GmailSender" + "message is NOT null");
			}

			transport = session.getTransport("smtps");
			transport.connect(HOST, 465, username, password);

			transport.sendMessage(message, message.getAllRecipients());

			transport.close();
			/* Transport.send(message); */

			System.out.println("GmailSender" + "Sent message.");
		} catch (AddressException ae) {
			ae.printStackTrace();
			System.out.println("GmailSender" +
					"AddressException - " + ae.getLocalizedMessage());
		} catch (MessagingException me) {
			me.printStackTrace();
			System.out.println("GmailSender" +
					"MessagingException - " + me.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GmailSender" + "Exception - " + e.getLocalizedMessage());
		}
	}

	public static class ByteArrayDataSource implements DataSource {
		private byte[] data;
		private String type;

		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getContentType() {
			if (type == null)
				return "application/octet-stream";
			else
				return type;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		public String getName() {
			return "ByteArrayDataSource";
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("Not Supported");
		}
	}
}
 