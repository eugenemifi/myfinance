package io.prozy.myfinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class MyfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfinanceApplication.class, args);
	}

}
