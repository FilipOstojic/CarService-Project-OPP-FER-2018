package hr.fer.opp.util;

import java.io.IOException;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
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

import hr.fer.opp.model.Appointment;
import hr.fer.opp.model.Autoservice;
import hr.fer.opp.model.User;
import hr.fer.opp.model.UserVehicle;

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

	public static PDDocument generatePDF(Appointment appointment, Autoservice autoservice) {
		UserVehicle vehicle = appointment.getVehicle();
		User user = vehicle.getOwner();
		User mech = appointment.getMechanic();
		
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		
		PDPageContentStream contentStream;
		try {
			Path path = Paths.get(ClassLoader.getSystemResource("lse.png").toURI());
			contentStream = new PDPageContentStream(document, page);
			PDImageXObject image = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
			
			int offsetW = image.getWidth()/2;
			int offsetH = image.getHeight() + 2*offsetW;
			int width = (int) page.getBBox().getWidth();
			int height = (int) page.getBBox().getHeight();
			
			contentStream.drawImage(image, offsetW, height - offsetW - image.getHeight());
			
			contentStream.setLeading(14.5f);
			
			contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 30);
            contentStream.newLineAtOffset(image.getWidth() + 2*offsetW, (int) (height - image.getHeight()*1.5));
            contentStream.showText(autoservice.getName());
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.newLineAtOffset(width - 3*offsetW, height - offsetH);
            contentStream.showText(autoservice.getAddress());
            contentStream.newLine();
            contentStream.showText(autoservice.getOib());
            contentStream.newLine();
            contentStream.showText(autoservice.getEmail());
            contentStream.newLine();
            contentStream.showText(autoservice.getMobile());
            contentStream.endText();
            
            drawRectangle(contentStream, height, width, offsetW, offsetH, image);
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 15);
            contentStream.newLineAtOffset(offsetW + 20, height - (int)(1.5*offsetH) - 40);
            contentStream.showText("Ovlašteni serviser:");
            contentStream.newLineAtOffset(20, -20);
            contentStream.showText("Ime i prezime: " + mech.getName() + " " + mech.getSurname());
            contentStream.newLine();
            contentStream.showText("Email: " + mech.getEmail());
            contentStream.newLine();
            contentStream.showText("Broj mobitela: " + mech.getMobile());
            contentStream.newLineAtOffset(-20, -40);
            contentStream.showText("Klijent:");
            contentStream.newLineAtOffset(20, -20);
            contentStream.showText("Ime i prezime: " + user.getName() + " " + user.getSurname());
            contentStream.newLine();
            contentStream.showText("Email: " + user.getEmail());
            contentStream.newLine();
            contentStream.showText("Broj mobitela: " + user.getMobile());
            contentStream.newLineAtOffset(-20, 40);
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 15);
            contentStream.newLineAtOffset(offsetW + 20, (height - (int)(1.5*offsetH) - offsetW - image.getHeight())/2 + offsetW + image.getHeight() - 40);
            contentStream.showText("Vozilo: ");
            contentStream.newLineAtOffset(20, -20);
            contentStream.showText("Registracija: " + vehicle.getLicensePlate());
            contentStream.newLine();
            contentStream.showText("Model: " + vehicle.getModel().getName());
            contentStream.newLine();
            contentStream.showText("Godina proizvodnje: " + vehicle.getYear());
            contentStream.newLineAtOffset(-20, -20);
            contentStream.showText("Usluga:");
            contentStream.newLineAtOffset(20, -20);
            contentStream.showText(appointment.getService().getName());
            contentStream.newLineAtOffset(-20, -20);
            contentStream.showText("Opis:");
            contentStream.newLineAtOffset(20, -20);
            String description = appointment.getDescription();
            contentStream.showText(description == null ? "" : description);
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.newLineAtOffset((int) (1.5*offsetW), image.getHeight());
            contentStream.showText("Ovlašteni serviser:");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
            contentStream.newLineAtOffset(width - (int) (1.5*offsetW) - 150, image.getHeight());
            contentStream.showText("Klijent:");
            contentStream.endText();
            
            contentStream.close();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		return document;
	}

	private static void drawRectangle(PDPageContentStream contentStream, int height, int width, int offsetW,
			int offsetH, PDImageXObject image) throws IOException {
		
		contentStream.moveTo(offsetW, height - (int)(1.5*offsetH));
        contentStream.lineTo(width - offsetW, height - (int)(1.5*offsetH));
        contentStream.lineTo(width - offsetW,  offsetW + image.getHeight());
        contentStream.lineTo(offsetW, offsetW + image.getHeight());
        contentStream.lineTo(offsetW, height - (int)(1.5*offsetH));
        contentStream.moveTo(offsetW, (height - (int)(1.5*offsetH) - offsetW - image.getHeight())/2 + offsetW + image.getHeight());
        contentStream.lineTo(width - offsetW, (height - (int)(1.5*offsetH) - offsetW - image.getHeight())/2 + offsetW + image.getHeight());
        contentStream.stroke();
        
        contentStream.moveTo((int) (1.5*offsetW), image.getHeight()/2);
        contentStream.lineTo((int) (1.5*offsetW) + 150, image.getHeight()/2);
        contentStream.stroke();
        
        contentStream.moveTo(width - (int)(1.5*offsetW), image.getHeight()/2);
        contentStream.lineTo(width - (int)(1.5*offsetW) - 150, image.getHeight()/2);
        contentStream.stroke();
  
	}

	public static boolean sendPeriodicEmail(Appointment appointment) {
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
		
		User user = appointment.getVehicle().getOwner();
		String email = user.getEmail();
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("LSE Najbolji mehanicar <lse.najboljimehanicar@gmail.com>"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSentDate(new Date());
			message.setSubject("Appointment scheduled for tomorrow!");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(appointment.getDate());
			
			String text = String.format("<h2>Dear %s, your appointment is scheduled for tomorrow</h2>"
					+ "<p>at %s</p>"
					+ "<p>Cheers</p>", user.getName(), cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));

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


}
