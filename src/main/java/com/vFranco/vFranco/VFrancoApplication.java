package com.vFranco.vFranco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


import com.vFranco.vFranco.securityConfig.CORSConfig;

@SpringBootApplication
@Import(CORSConfig.class)
public class VFrancoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VFrancoApplication.class, args);
	}

}
