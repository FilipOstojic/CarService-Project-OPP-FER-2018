package autoservis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="autoservis")
public class AutoservisApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoservisApplication.class, args);
	}
}
