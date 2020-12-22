package br.com.indtextbr.services.logistica.repository;

import org.springframework.stereotype.Repository;
import br.com.indtextbr.services.logistica.entity.Armazem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ArmazemRepository extends JpaRepository<Armazem, String> {

	List<Armazem> findAllByStatus(String statusAtivo);

	Armazem findByIdAndStatus(String id, String statusAtivo);

}
