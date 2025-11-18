package br.ucsal.Program_service.service;

// Removido import de Professor e ProfessorRepository
import br.ucsal.Program_service.dto.HistoricoProgramaDTO;
import br.ucsal.Program_service.model.entity.HistoricoPrograma;
import br.ucsal.Program_service.model.entity.Programa;
import br.ucsal.Program_service.repository.HistoricoProgramaRepository;
import br.ucsal.Program_service.repository.ProgramaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoricoProgramaService {

    private final HistoricoProgramaRepository historicoRepository;
    private final ProgramaRepository programaRepository;
    // private final ProfessorRepository professorRepository; // <-- REMOVIDO

    // Construtor limpo, sem ProfessorRepository
    public HistoricoProgramaService(HistoricoProgramaRepository historicoRepository,
                                    ProgramaRepository programaRepository) {
        this.historicoRepository = historicoRepository;
        this.programaRepository = programaRepository;
    }

    public HistoricoPrograma salvar(HistoricoProgramaDTO dto) {
        Programa programa = programaRepository.findById(dto.programaId())
                .orElseThrow(() -> new RuntimeException("Programa não encontrado"));

        // ANTES: buscava o professor no banco
        // AGORA: apenas usa o ID do DTO
        Integer professorId = dto.professorId();

        HistoricoPrograma historico = new HistoricoPrograma(
                programa,
                professorId, // Passa o ID
                dto.descricao()
        );
        historico.setDataAtualizacao(dto.dataAtualizacao() != null ? dto.dataAtualizacao() : LocalDateTime.now());

        return historicoRepository.save(historico);
    }

    // Métodos internos chamados pelo ProgramaService (atualizados para receber ID)
    public void registrarAtualizacao(Programa programa, Integer professorId, String descricao) {
        HistoricoPrograma historico = new HistoricoPrograma(programa, professorId, descricao);
        historicoRepository.save(historico);
    }

    public void registrarFinalizacao(Programa programa, Integer professorId) {
        HistoricoPrograma historico = new HistoricoPrograma(
                programa,
                professorId,
                "Programa finalizado com sucesso"
        );
        historicoRepository.save(historico);
    }

    public List<HistoricoPrograma> listar() {
        return historicoRepository.findAll();
    }
}