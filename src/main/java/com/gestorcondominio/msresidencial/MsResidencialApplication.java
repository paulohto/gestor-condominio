package com.gestorcondominio.msresidencial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsResidencialApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsResidencialApplication.class, args);
	}

}
