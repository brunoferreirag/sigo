package br.com.indtextbr.services.sigoapierp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import br.com.indtextbr.services.sigoapierp.service.SetorIndustrialService;

@Configuration
@RefreshScope
public class GestaoProcessosIndustriaisConfiguration {
	@Value("${sigo-api-erp.endereco-ws}")
	private String enderecoWS;
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("br.com.indtextbr.services.sigoerp.wsdl");
		return marshaller;
	}

	@Bean
	public SetorIndustrialService paradaProducaoService(Jaxb2Marshaller marshaller) {
		SetorIndustrialService client = new SetorIndustrialService();
		client.setDefaultUri(this.enderecoWS);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
	
	@Bean
	public SetorIndustrialService setorIndustrialService(Jaxb2Marshaller marshaller) {
		SetorIndustrialService client = new SetorIndustrialService();
		client.setDefaultUri(this.enderecoWS);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
