package br.ucsal.Program_service.repository;

import br.ucsal.Program_service.model.entity.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Integer> {
    List<Programa> findByAtivo(boolean ativo);
}