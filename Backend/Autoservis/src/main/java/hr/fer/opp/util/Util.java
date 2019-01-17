package hr.fer.opp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;

import hr.fer.opp.model.Appointment;
import hr.fer.opp.model.Autoservice;
import hr.fer.opp.model.User;
import hr.fer.opp.model.UserVehicle;

public class Util {
	
	public static final Locale LOCALE = new Locale("hr", "HR");
	public static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", LOCALE);
	
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
			StringBuilder sb = new StringBuilder();
			sb.append("http://najbolji-mehanicar.us-east-2.elasticbeanstalk.com/");
			sb.append("user/invitation/");
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
	
	public static PDDocument generatePDF(Appointment appointment, Autoservice autoservice) {
		UserVehicle vehicle = appointment.getVehicle();
		User user = vehicle.getOwner();
		User mech = appointment.getMechanic();
		
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		
		PDFont font;
		try {
			font = PDType0Font.load(document, new ClassPathResource("DejaVuSans.ttf").getInputStream());
		} catch (IOException e1) {
			font = PDType1Font.TIMES_ROMAN;
			e1.printStackTrace();
		}
		
		PDPageContentStream contentStream;
		try {
			contentStream = new PDPageContentStream(document, page);
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int nRead;
		    byte[] data = new byte[1024];
		    InputStream is = new ClassPathResource("lse.png").getInputStream();
		    while ((nRead = is.read(data, 0, data.length)) != -1) {
		        buffer.write(data, 0, nRead);
		    }
		 
		    buffer.flush();
		    byte[] byteArray = buffer.toByteArray();
			PDImageXObject image = PDImageXObject.createFromByteArray(document, byteArray, null);
			
			int offsetW = image.getWidth()/2;
			int offsetH = image.getHeight() + 2*offsetW;
			int width = (int) page.getBBox().getWidth();
			int height = (int) page.getBBox().getHeight();
			
			contentStream.drawImage(image, offsetW, height - offsetW - image.getHeight());
			
			contentStream.setLeading(14.5f);
			
			contentStream.beginText();
            contentStream.setFont(font, 30);
            contentStream.newLineAtOffset(image.getWidth() + 2*offsetW, (int) (height - image.getHeight()*1.5));
            contentStream.showText(autoservice.getName());
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.newLineAtOffset(width - 4*offsetW, height - offsetH);
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
            contentStream.setFont(font, 15);
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
            contentStream.setFont(font, 15);
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
            contentStream.newLineAtOffset(-20, -20);
            contentStream.showText("Zamjensko vozilo: " + (appointment.isRepVehicle() ? "DA" : "NE"));
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.newLineAtOffset((int) (1.5*offsetW), image.getHeight());
            contentStream.showText("Ovlašteni serviser:");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.setFont(font, 10);
            contentStream.newLineAtOffset(width - (int) (1.5*offsetW) - 150, image.getHeight());
            contentStream.showText("Klijent:");
            contentStream.endText();
            
            contentStream.close();
		} catch (IOException e) {
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

	public static List<String> getAvailableAppointments(List<Appointment> appointments) throws ParseException {
		List<String> available = new ArrayList<>();
		
		Calendar calStart = Calendar.getInstance();
		calStart.add(Calendar.DAY_OF_MONTH, 1);
		calStart.set(Calendar.HOUR_OF_DAY, 7);
		calStart.set(Calendar.MINUTE, 0);
	    calStart.set(Calendar.SECOND, 0);
	    
	    Calendar calEnd = (Calendar) calStart.clone();
	    
	    while (true) {
	    	if (available.size() == 120) {
				break;
			}
			if (ChronoUnit.DAYS.between(calStart.toInstant(), calEnd.toInstant()) > 10) {
				break;
			}
			boolean found = false;
			for (Appointment a : appointments) {
				Calendar tmp = Calendar.getInstance();
				tmp.setTime(a.getDate());
				if (Util.areEqualDates(calEnd, tmp)) {
					found = true;
					continue;
				}
			}
		
			if (!found) {
				available.add(SDF.format(calEnd.getTime()));
			}
			
			calEnd.add(Calendar.MINUTE, 20);
			if (calEnd.get(Calendar.HOUR_OF_DAY) == 10 && calEnd.get(Calendar.MINUTE) == 0) {
				calEnd.add(Calendar.DAY_OF_MONTH, 1);
				calEnd.set(Calendar.HOUR_OF_DAY, 7);
			}
		}
	    
	    return available;
	}

	private static boolean areEqualDates(Calendar first, Calendar second) {
		if (first.get(Calendar.YEAR) != second.get(Calendar.YEAR)) {
			return false;
		}
		if (first.get(Calendar.MONTH) != second.get(Calendar.MONTH)) {
			return false;
		}
		if (first.get(Calendar.DAY_OF_YEAR) != second.get(Calendar.DAY_OF_YEAR)) {
			return false;
		}
		if (first.get(Calendar.HOUR_OF_DAY) != second.get(Calendar.HOUR_OF_DAY)) {
			return false;
		}
		if (first.get(Calendar.MINUTE) != second.get(Calendar.MINUTE)) {
			return false;
		}
		if (first.get(Calendar.SECOND) != second.get(Calendar.SECOND)) {
			return false;
		}
		return true;
	}
	
	public static boolean areEqualDay(Calendar first, Calendar second) {
		if (first.get(Calendar.YEAR) != second.get(Calendar.YEAR)) {
			return false;
		}
		if (first.get(Calendar.MONTH) != second.get(Calendar.MONTH)) {
			return false;
		}
		if (first.get(Calendar.DAY_OF_YEAR) != second.get(Calendar.DAY_OF_YEAR)) {
			return false;
		}
		return true;
	}

	public static Map<String, Integer> getAvailable(List<Appointment> appointments) {
		Map<String, Integer> available = new LinkedHashMap<>();
		
		Calendar calStart = Calendar.getInstance();
		calStart.add(Calendar.DAY_OF_MONTH, 1);
		calStart.set(Calendar.HOUR_OF_DAY, 7);
		calStart.set(Calendar.MINUTE, 0);
	    calStart.set(Calendar.SECOND, 0);
	    
	    Calendar calEnd = (Calendar) calStart.clone();
	    
	    while (true) {
	    	if (available.size() == 120) {
				break;
			}
			if (ChronoUnit.DAYS.between(calStart.toInstant(), calEnd.toInstant()) > 10) {
				break;
			}
			
			String date = SDF.format(calEnd.getTime());
			available.put(date, 0);
	
			for (Appointment a : appointments) {
				Calendar tmp = Calendar.getInstance();
				tmp.setTime(a.getDate());
				if (Util.areEqualDates(calEnd, tmp)) {
					Integer value = available.get(date);
					available.put(date, ++value);
				}
			}
			
			calEnd.add(Calendar.MINUTE, 20);
			if (calEnd.get(Calendar.HOUR_OF_DAY) == 10 && calEnd.get(Calendar.MINUTE) == 0) {
				calEnd.add(Calendar.DAY_OF_MONTH, 1);
				calEnd.set(Calendar.HOUR_OF_DAY, 7);
			}
		}
	    
	    return available;
	}
	
	public static boolean isMechFree(Date date, List<Appointment> appointments) {
		boolean found = false;
		for (Appointment a : appointments) {
			Calendar first = Calendar.getInstance();
			first.setTime(date);
			Calendar second = Calendar.getInstance();
			second.setTime(a.getDate());
			if (Util.areEqualDates(first, second)) {
				found = true;
				break;
			}
		}
		return !found;
	}
	
}
