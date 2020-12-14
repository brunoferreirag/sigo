package br.com.indtextbr.services.sigoapierp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SigoApiErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigoApiErpApplication.class, args);
	}

}
