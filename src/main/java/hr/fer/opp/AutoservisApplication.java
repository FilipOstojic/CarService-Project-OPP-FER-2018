package hr.fer.opp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages="hr.fer.opp")
@EnableScheduling
public class AutoservisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoservisApplication.class, args);
	}
}
