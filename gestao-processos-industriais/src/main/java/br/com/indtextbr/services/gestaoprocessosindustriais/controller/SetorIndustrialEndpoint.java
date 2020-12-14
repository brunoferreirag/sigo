package br.com.indtextbr.services.gestaoprocessosindustriais.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import br.com.indtextbr.services.gestaoprocessosindustriais.entity.DeleteSetorIndustrialRequest;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.DeleteSetorIndustrialResponse;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.GetSetorIndustrialByIdRequest;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.GetSetorIndustrialRequest;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.GetSetorIndustrialResponse;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.InsertSetorIndustrialRequest;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.InsertSetorIndustrialResponse;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.SetorIndustrialResponse;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.UpdateSetorIndustrialRequest;
import br.com.indtextbr.services.gestaoprocessosindustriais.entity.UpdateSetorIndustrialResponse;
import br.com.indtextbr.services.gestaoprocessosindustriais.service.SetorIndustrialService;

import org.springframework.ws.server.endpoint.annotation.Endpoint;

@Endpoint
public class SetorIndustrialEndpoint {
	private static final String NAMESPACE_URI = "http://indtextbr.com.br/services/gestaoprocessosindustriais/entity";

	private SetorIndustrialService service;

	@Autowired
	public SetorIndustrialEndpoint(SetorIndustrialService setorIndustrialService) {
		this.service = setorIndustrialService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSetorIndustrialRequest")
	@ResponsePayload
	public GetSetorIndustrialResponse getSetorIndustrial(@RequestPayload GetSetorIndustrialRequest request) {
		GetSetorIndustrialResponse response = new GetSetorIndustrialResponse();
		response.getLista().addAll(service.getAllSetorIndustrial());
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "insertSetorIndustrialRequest")
	@ResponsePayload
	public InsertSetorIndustrialResponse incluirSetorIndustrial(@Valid @RequestPayload InsertSetorIndustrialRequest request) {
		var setorIncluido = this.service.incluirSetorIndustrial(request.getSetorIndustrial());
		InsertSetorIndustrialResponse response = new InsertSetorIndustrialResponse();
		response.setMensagem("Setor industrial : " + setorIncluido.getId() + " incluído com sucesso!");
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateSetorIndustrialRequest")
	@ResponsePayload
	public UpdateSetorIndustrialResponse updateSetorIndustrial(@Valid @RequestPayload UpdateSetorIndustrialRequest request) {
		var setorIncluido = this.service.incluirSetorIndustrial(request.getSetorIndustrial());
		UpdateSetorIndustrialResponse response = new UpdateSetorIndustrialResponse();
		response.setMensagem("Setor industrial : " + setorIncluido.getId() + " atualizado com sucesso!");
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteSetorIndustrialRequest")
	@ResponsePayload
	public DeleteSetorIndustrialResponse excluirSetorIndustrial(@Valid @RequestPayload DeleteSetorIndustrialRequest request) {
		this.service.excluirSetorIndustrial(request.getId());
		DeleteSetorIndustrialResponse response = new DeleteSetorIndustrialResponse();
		response.setMensagem("Setor industrial : " + request.getId() + " excluído com sucesso!");
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSetorIndustrialByIdRequest")
	@ResponsePayload
	public SetorIndustrialResponse getSetorIndustrialById(@Valid @RequestPayload GetSetorIndustrialByIdRequest request) {
		SetorIndustrialResponse response = new SetorIndustrialResponse();
		response.setSetorIndustrial(service.getSetorIndustrialPorId(request.getId()));
		return response;
	}
}