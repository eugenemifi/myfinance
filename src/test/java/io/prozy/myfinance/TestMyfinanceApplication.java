package io.prozy.myfinance;

import org.springframework.boot.SpringApplication;

public class TestMyfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.from(MyfinanceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
