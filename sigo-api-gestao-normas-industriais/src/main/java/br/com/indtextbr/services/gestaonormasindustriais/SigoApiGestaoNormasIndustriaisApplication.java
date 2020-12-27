package br.com.indtextbr.services.gestaonormasindustriais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SigoApiGestaoNormasIndustriaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SigoApiGestaoNormasIndustriaisApplication.class, args);
	}

}
