package hr.fer.opp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import hr.fer.opp.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}		
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			  .disable()
			    .authorizeRequests()
			      .antMatchers("/role/**").hasRole("ADMIN")
			      .antMatchers("/model/**").hasRole("ADMIN")
			      .antMatchers("/autoservice/**").hasRole("ADMIN")
			      .antMatchers("/service/**").hasRole("ADMIN")
			      .antMatchers("/serviceVehicle/**").hasRole("ADMIN")
			      .antMatchers("/serviceVehicle/free/**").hasRole("MECH")
			      .antMatchers("/userVehicle/**").hasAnyRole("ADMIN", "USER")
			      .antMatchers("/appointment/**").hasAnyRole("ADMIN", "MECH")
			      .antMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
			      // ne znam hoce li ovo biti problem kasnije...
			      .antMatchers(HttpMethod.GET, "/user/loggedIn").hasRole("USER")
			      .antMatchers(HttpMethod.POST, "/user").hasAnyRole("ADMIN", "USER", "MECH")
			      .antMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("ADMIN", "USER", "MECH")

			      .and()
			      	.logout()
			      	  .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)));
//			      	  	.logoutSuccessUrl("vrati na homepage");
						
		// .and().httpBasic();
		// ako se odkomentira ovo iznad i iduca metoda onda se
		// preko postmana moze radit basic authorization, ali onda ne radi samo s loginom...
		// uopce se ne gleda tko je ulogiran nego samo trenutna autorizacija
		// pretpostavljam da je ovako lakše s angularom radi
	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//	   web.ignoring().antMatchers("/login");
//	}
	
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
