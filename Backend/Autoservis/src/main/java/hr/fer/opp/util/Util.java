package hr.fer.opp.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.itextpdf.text.pdf.PdfDocument;

import hr.fer.opp.model.Appointment;
import hr.fer.opp.model.User;

public class Util {
	
	public static boolean sendEmail(User user, ServletContext servletContext) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("lse.najboljimehanicar@gmail.com", "littleskillzexception");
			}; 
		});
		
		String email = user.getEmail();
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("LSE Najbolji mehanicar <lse.najboljimehanicar@gmail.com>"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSentDate(new Date());
			message.setSubject("Confirm Your Email");
			
			// ovdje ce biti problema, bezz http nije prepoznavao kao link
			// s localhost se u mailu uopce ne prikazuje kao link...
			StringBuilder sb = new StringBuilder("localhost:8080");
			sb.append(servletContext.getContextPath()).append("/user/invitation/");
			sb.append(email);
			
			String text = String.format("<h2>Dear %s, please confirm your email address</h2>"
					+ "<p>Click the link below to confirm your email address:</p>"
					+ "<p><a href=\"%s\">%s</a><p>"
					+ "<p>Cheers</p>", user.getName(), sb.toString(), "Link");

			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setText(text, "UTF-8", "html");
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(mbp);
			
			message.setContent(mp);
			
			Transport.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private static final int SHIFT = 4;
	private static final int AND_BITS = 0xf;
	private static final int ASCII_LOWER = 87;
	private static final int ASCII_NUMBER = 48;
	
	public static String digestPassword(String password) {
		if (password == null || password.isEmpty()) {
			return "";
		}
		String passDigest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(password.getBytes());
			passDigest = bytetohex(md.digest());
		} catch (NoSuchAlgorithmException e) {
		}
		return passDigest;
	}
	
	private static String bytetohex(byte[] hex) {
		StringBuilder sb = new StringBuilder("");
		int len = hex.length;
		for (int i = 0; i < len; i++) {
			char little = numberToChar(hex[i] & AND_BITS);
			char big = numberToChar((hex[i] >> SHIFT) & AND_BITS);
			sb.append(big);
			sb.append(little);
		}
		return sb.toString();
	}
	
	private static char numberToChar(int number) {
		if (number < 10) {
			return (char)(number + ASCII_NUMBER);
		}
		return (char)(number + ASCII_LOWER);
	}

	public static PDDocument generatePDF(Appointment appointment) {
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		 
		PDPageContentStream contentStream;
		try {
			contentStream = new PDPageContentStream(document, page);
			contentStream.setFont(PDType1Font.COURIER, 12);
			contentStream.beginText();
			contentStream.showText("Hello World");
			contentStream.endText();
			contentStream.close();
			 
			Path path = Paths.get(ClassLoader.getSystemResource("lse.jpg").toURI());
			contentStream = new PDPageContentStream(document, page);
			PDImageXObject image = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
			contentStream.drawImage(image, image.getWidth(), image.getHeight());
			contentStream.close();

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return document;
	}
}
