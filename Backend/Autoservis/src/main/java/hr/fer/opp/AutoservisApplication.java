package hr.fer.opp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="hr.fer.opp")
public class AutoservisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoservisApplication.class, args);
	}
}
