package com.gestorcondominio.msresidencial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsResidencialApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsResidencialApplication.class, args);
	}

}
