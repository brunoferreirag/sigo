package br.com.indtextbr.services.sigoapilogistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SigoApiLogisticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigoApiLogisticaApplication.class, args);
	}

}
