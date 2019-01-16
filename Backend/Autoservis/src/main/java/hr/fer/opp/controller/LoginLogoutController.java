package hr.fer.opp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.opp.security.SecurityConfig;

@RestController
@CrossOrigin("*")
public class LoginLogoutController {

	@Autowired
	private SecurityConfig securityConfig;

	private static final String KEY_ERROR = "ERROR";
	private static final String KEY_STATUS = "STATUS";

	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> params,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.OK;

		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(params.get("username"),
					params.get("password"));
			Authentication auth = securityConfig.getAuthenticationManager().authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);

			status = HttpStatus.OK;
		} catch (BadCredentialsException ex) {
			status = HttpStatus.BAD_REQUEST;
			result.put(KEY_ERROR, ex.getMessage());
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			result.put(KEY_ERROR, e.getMessage());
		}

		result.put(KEY_STATUS, status);
		return new ResponseEntity<Map<String, Object>>(result, status);
	}

	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.OK;
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
			status = HttpStatus.OK;
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			result.put(KEY_ERROR, e.getMessage());
		}

		result.put(KEY_STATUS, status);
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> logoutGet(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.OK;
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}
			status = HttpStatus.OK;
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			result.put(KEY_ERROR, e.getMessage());
		}

		result.put(KEY_STATUS, status);
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
}
