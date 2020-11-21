package br.com.indtextbr.services.gestaoprocessosindustriais.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.GetProblemaProducaoRequest;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.GetProblemaProducaoResponse;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.ProblemaProducao;
import br.com.indtextbr.services.gestaoprocessosindustriais.repository.ProblemaProducaoRepository;


import org.springframework.ws.server.endpoint.annotation.Endpoint;

@Endpoint
public class ProblemaProducaoEndpoint {
	private static final String NAMESPACE_URI = "http://indtextbr.com.br/services/gestaoprocessosindustriais/entity";

	private ProblemaProducaoRepository repository;

	@Autowired
	public ProblemaProducaoEndpoint(ProblemaProducaoRepository problemaProducaoRepository) {
		this.repository = problemaProducaoRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProblemaProducaoRequest")
	@ResponsePayload
	public GetProblemaProducaoResponse getCountry(@RequestPayload GetProblemaProducaoRequest request) {
		GetProblemaProducaoResponse response = new GetProblemaProducaoResponse();
		List<ProblemaProducao> paradas = this.repository.getByTurnoDataInicioAndDataFim(request.getTurno(), request.getDataInicio(), request.getDataFim());
	    response.getLista().addAll(paradas);
		return response;
	}
}