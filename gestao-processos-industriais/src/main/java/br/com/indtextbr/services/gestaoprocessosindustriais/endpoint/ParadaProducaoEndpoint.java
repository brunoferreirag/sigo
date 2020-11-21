package br.com.indtextbr.services.gestaoprocessosindustriais.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import br.com.indtextbr.services.gestaoprocessosindustriais.entity.GetParadaProducaoRequest;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.GetParadaProducaoResponse;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.ParadaProducao;
import br.com.indtextbr.services.gestaoprocessosindustriais.repository.ParadaProducaoRepository;


import org.springframework.ws.server.endpoint.annotation.Endpoint;

@Endpoint
public class ParadaProducaoEndpoint {
	private static final String NAMESPACE_URI = "http://indtextbr.com.br/services/gestaoprocessosindustriais/entity";

	private ParadaProducaoRepository repository;

	@Autowired
	public ParadaProducaoEndpoint(ParadaProducaoRepository paradaProducaoRepository) {
		this.repository = paradaProducaoRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getParadaProducaoRequest")
	@ResponsePayload
	public GetParadaProducaoResponse getParadaProducao(@RequestPayload GetParadaProducaoRequest request) {
		GetParadaProducaoResponse response = new GetParadaProducaoResponse();
		List<ParadaProducao> paradas = this.repository.getByTurnoAndDataInicioAndDataFim(request.getTurno(), request.getDataInicio(), request.getDataFim());
	    response.getLista().addAll(paradas);
		return response;
	}
}