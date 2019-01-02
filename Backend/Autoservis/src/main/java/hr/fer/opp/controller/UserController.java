package hr.fer.opp.controller;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.PasswordAuthentication;

import hr.fer.opp.model.User;
import hr.fer.opp.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listModels() {
		List<User> list = userService.listAll();
		if (list != null) {
			return new ResponseEntity<>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user/invitation/{email}", method = RequestMethod.GET)
	public ResponseEntity<User> confirmEmail(@PathVariable("email") String email) {
		try {
			User user = userService.showRecord(email);
			user.setConfirmed(true);
			userService.updateRecord(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<User> crateModel(@RequestBody User user) {
		boolean created = userService.createRecord(user);
		
		if (created) {
			// sto ako slanje mail ne uspije??
			if (sendEmail(user)) {
				System.out.println("Poslano");
			}
			
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> updateModel(@RequestBody User user) {
		try {
			userService.updateRecord(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/user/{email}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<User> deleteModel(@PathVariable("email") String email) {
		try {
			User user = userService.showRecord(email);
			userService.deleteRecord(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private boolean sendEmail(User user) {
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
			message.setFrom(new InternetAddress("lse.najboljimehanicar@gmail.com"));
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
}
