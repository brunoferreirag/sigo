package br.com.indtextbr.services.logistica.repository;

import org.springframework.stereotype.Repository;
import br.com.indtextbr.services.logistica.entity.Armazem;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface ArmazemRepository extends PagingAndSortingRepository<Armazem, String> {

	Page<Armazem> findAllByStatus(String statusAtivo,Pageable pageable);

	Armazem findByIdAndStatus(String id, String statusAtivo);

}
