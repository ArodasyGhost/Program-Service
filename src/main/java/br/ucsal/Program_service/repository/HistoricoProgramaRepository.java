package br.ucsal.Program_service.repository;

import br.ucsal.Program_service.model.entity.HistoricoPrograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoProgramaRepository extends JpaRepository<HistoricoPrograma, Integer> {
}