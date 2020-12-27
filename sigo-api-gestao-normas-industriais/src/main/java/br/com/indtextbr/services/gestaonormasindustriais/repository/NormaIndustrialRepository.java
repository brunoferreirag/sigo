package br.com.indtextbr.services.gestaonormasindustriais.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.indtextbr.services.gestaonormasindustriais.common.Status;
import br.com.indtextbr.services.gestaonormasindustriais.model.NormaIndustrial;


@Repository
public interface NormaIndustrialRepository extends MongoRepository<NormaIndustrial, String> {
	List<NormaIndustrial> findByStatus(Status status);
	
	List<NormaIndustrial> findByIdLikeAndTituloLikeAndVersaoAndAutorLikeAndConteudoLikeAndStatus(String id, String titulo,String versao, String autor, String conteudo, Status status);
}
