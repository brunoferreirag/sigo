package br.com.indtextbr.services.gestaoprocessosindustriais.repository;

import org.springframework.stereotype.Repository;
import  br.com.indtextbr.services.gestaoprocessosindustriais.entity.SetorIndustrialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SetorIndustrialRepository extends JpaRepository<SetorIndustrialEntity, Integer> {

}
