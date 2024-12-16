package com.gabrielsena.agregadordeinvestimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AgregadordeinvestimentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgregadordeinvestimentoApplication.class, args);
	}

}
