package br.com.indtextbr.services.sigoapierp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import br.com.indtextbr.services.sigoapierp.model.AtividadeSetorIndustrial;
import br.com.indtextbr.services.sigoapierp.model.SetorIndustrial;
import br.com.indtextbr.services.sigoerp.wsdl.AtividadeIndustrial;
import br.com.indtextbr.services.sigoerp.wsdl.DeleteSetorIndustrialRequest;
import br.com.indtextbr.services.sigoerp.wsdl.DeleteSetorIndustrialResponse;
import br.com.indtextbr.services.sigoerp.wsdl.GetSetorIndustrialByIdRequest;
import br.com.indtextbr.services.sigoerp.wsdl.GetSetorIndustrialRequest;
import br.com.indtextbr.services.sigoerp.wsdl.GetSetorIndustrialResponse;
import br.com.indtextbr.services.sigoerp.wsdl.InsertSetorIndustrialRequest;
import br.com.indtextbr.services.sigoerp.wsdl.InsertSetorIndustrialResponse;
import br.com.indtextbr.services.sigoerp.wsdl.SetorIndustrialResponse;
import br.com.indtextbr.services.sigoerp.wsdl.UpdateSetorIndustrialRequest;
import br.com.indtextbr.services.sigoerp.wsdl.UpdateSetorIndustrialResponse;


public class SetorIndustrialService extends WebServiceGatewaySupport {
	private static final String SUCESSO = "sucesso";
	public SetorIndustrialService() {
		
	}

	public List<SetorIndustrial> getAllSetoresIndustriais() {
		GetSetorIndustrialRequest request = new GetSetorIndustrialRequest();
		GetSetorIndustrialResponse response =(GetSetorIndustrialResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		var lista = response.getLista();
		List<SetorIndustrial> retorno = new ArrayList<>();
		lista.forEach(p-> {
			SetorIndustrial setorIndustrial = converterSetorIndustrialWsParaSetorIndustrialRest(p);
			
			retorno.add(setorIndustrial);
		});
		return retorno;
	}

	private SetorIndustrial converterSetorIndustrialWsParaSetorIndustrialRest(
			br.com.indtextbr.services.sigoerp.wsdl.SetorIndustrial p) {
		SetorIndustrial setorIndustrial = new SetorIndustrial();
		setorIndustrial.setId(p.getId());
		setorIndustrial.setNome(p.getNome());
		if(p.getListaAtividades()!= null && !p.getListaAtividades().isEmpty()) {
			p.getListaAtividades().forEach(q ->{
				AtividadeSetorIndustrial atividade = new AtividadeSetorIndustrial();
				atividade.setExecutaDomingo(q.getExecutaDomingo());
				atividade.setExecutaSabado(q.getExecutaSabado());
				atividade.setExecutaSexta(q.getExecutaSexta());
				atividade.setExecutaQuinta(q.getExecutaQuinta());
				atividade.setExecutaQuarta(q.getExecutaQuarta());
				atividade.setExecutaTerca(q.getExecutaTerca());
				atividade.setExecutaSegunda(q.getExecutaSegunda());
				atividade.setNome(q.getNome());
				atividade.setId(q.getId());
				setorIndustrial.getAtividades().add(atividade);
			});
		}
		return setorIndustrial;
	}
	
	public SetorIndustrial getSetorIndustrialById(int id) {
		GetSetorIndustrialByIdRequest request = new GetSetorIndustrialByIdRequest();
		request.setId(id);
		SetorIndustrialResponse response =(SetorIndustrialResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		if(response.getSetorIndustrial()!=null) {
			SetorIndustrial setorIndustrial = converterSetorIndustrialWsParaSetorIndustrialRest(response.getSetorIndustrial());
			return setorIndustrial;
		}
		return null;
	}
	
	public boolean excluirSetorIndustrial(int id) {
		DeleteSetorIndustrialRequest request = new DeleteSetorIndustrialRequest();
		request.setId(id);
		DeleteSetorIndustrialResponse response =(DeleteSetorIndustrialResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		
		return response.getMensagem().toLowerCase().contains(SUCESSO);
	}
	
	public boolean incluirSetorIndustrial(SetorIndustrial setor) {
		InsertSetorIndustrialRequest request = new InsertSetorIndustrialRequest();
		request.setSetorIndustrial(new br.com.indtextbr.services.sigoerp.wsdl.SetorIndustrial());
		request.getSetorIndustrial().setNome(setor.getNome());
		
		if(setor.getAtividades()!=null && !setor.getAtividades().isEmpty()) {
			setor.getAtividades().forEach(p ->{
				AtividadeIndustrial atividade = new AtividadeIndustrial();
				atividade.setExecutaDomingo(p.getExecutaDomingo());
				atividade.setExecutaQuarta(p.getExecutaQuarta());
				atividade.setExecutaQuinta(p.getExecutaQuinta());
				atividade.setExecutaSabado(p.getExecutaSabado());
				atividade.setExecutaSegunda(p.getExecutaSegunda());
				atividade.setExecutaSexta(p.getExecutaSexta());
				atividade.setExecutaTerca(p.getExecutaTerca());
				atividade.setNome(p.getNome());
				request.getSetorIndustrial().getListaAtividades().add(atividade);
			});
		}
		
		InsertSetorIndustrialResponse response =(InsertSetorIndustrialResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		
		return response.getMensagem().toLowerCase().contains(SUCESSO);
	}
	
	public boolean atualizarSetorIndustrial(SetorIndustrial setor) {
		UpdateSetorIndustrialRequest request = new UpdateSetorIndustrialRequest();
		request.getSetorIndustrial().setNome(setor.getNome());
		request.getSetorIndustrial().setId(setor.getId());
		
		if(setor.getAtividades()!=null && !setor.getAtividades().isEmpty()) {
			setor.getAtividades().forEach(p ->{
				AtividadeSetorIndustrial atividade = new AtividadeSetorIndustrial();
				atividade.setExecutaDomingo(p.getExecutaDomingo());
				atividade.setExecutaQuarta(p.getExecutaQuarta());
				atividade.setExecutaQuinta(p.getExecutaQuinta());
				atividade.setExecutaSabado(p.getExecutaSabado());
				atividade.setExecutaSegunda(p.getExecutaSegunda());
				atividade.setExecutaSexta(p.getExecutaSexta());
				atividade.setExecutaTerca(p.getExecutaTerca());
				atividade.setNome(p.getNome());
				setor.getAtividades().add(atividade);
			});
		}
		
		UpdateSetorIndustrialResponse response =(UpdateSetorIndustrialResponse) getWebServiceTemplate().marshalSendAndReceive(request);
		
		return response.getMensagem().toLowerCase().contains(SUCESSO);
	}
}
