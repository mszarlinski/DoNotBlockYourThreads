package pl.mszarlinski.concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoNotBlockYourThreadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoNotBlockYourThreadsApplication.class, args);
	}
}
