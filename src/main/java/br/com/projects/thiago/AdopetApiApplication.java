package br.com.projects.thiago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdopetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdopetApiApplication.class, args);
	}

}
