package br.ucsal.Program_service.service;

import br.ucsal.Program_service.dto.HistoricoProgramaDTO;
import br.ucsal.Program_service.dto.ProgramaDTO;
import br.ucsal.Program_service.model.entity.Programa;
import br.ucsal.Program_service.repository.ProgramaRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgramaService {

    private final ProgramaRepository programaRepository;
    private final HistoricoProgramaService historicoService;
    // REMOVIDOS DisciplinaRepository e MatrizRepository

    // Construtor limpo
    public ProgramaService(ProgramaRepository programaRepository,
                           HistoricoProgramaService historicoService) {
        this.programaRepository = programaRepository;
        this.historicoService = historicoService;
    }

    // Criar novo programa (agora "burro", apenas salva dados)
    public Programa criarPrograma(ProgramaDTO dto) {
        // LÓGICA DE VALIDAÇÃO (se disciplina/matriz existem) FOI REMOVIDA
        // O Orquestrador é quem deve validar ANTES de chamar este método.

        Programa programa = new Programa();
        programa.setDisciplinaId(dto.disciplina_id()); // Apenas salva o ID
        programa.setMatrizId(dto.matriz_id());       // Apenas salva o ID
        programa.setSemestre(dto.semestre());
        programa.setEmenta(dto.ementa());
        programa.setObjetivos(dto.objetivos());
        programa.setConteudoProgramatico(dto.conteudoProgramatico());
        programa.setMetodologia(dto.metodologia());
        programa.setAvaliacao(dto.avaliacao());
        programa.setAtivo(dto.ativo());

        Programa salvo = programaRepository.save(programa);

        // Registrar histórico (passando 'null' para professorId na criação)
        historicoService.salvar(new HistoricoProgramaDTO(
                null, salvo.getId(), null, LocalDateTime.now(), "Programa criado"
        ));

        return salvo;
    }

    // Listar todos (sem mudança)
    public List<Programa> listarProgramas() {
        return programaRepository.findAll();
    }

    // Listar por status (sem mudança)
    public List<Programa> listarPorStatus(boolean ativo) {
        return programaRepository.findByAtivo(ativo);
    }

    // Buscar por ID (sem mudança)
    public Programa buscarPorId(Integer id) {
        return programaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programa não encontrado"));
    }

    // Atualizar programa (agora "burro")
    public Programa atualizarPrograma(Integer id, ProgramaDTO dto, Integer professorId) {
        Programa existente = buscarPorId(id);

        // LÓGICA DE VALIDAÇÃO REMOVIDA

        existente.setDisciplinaId(dto.disciplina_id());
        existente.setMatrizId(dto.matriz_id());
        existente.setSemestre(dto.semestre());
        existente.setEmenta(dto.ementa());
        existente.setObjetivos(dto.objetivos());
        existente.setConteudoProgramatico(dto.conteudoProgramatico());
        existente.setMetodologia(dto.metodologia());
        existente.setAvaliacao(dto.avaliacao());
        existente.setAtivo(dto.ativo());
        existente.setAtualizadoPorProfessorId(professorId); // Apenas salva o ID

        Programa atualizado = programaRepository.save(existente);

        historicoService.registrarAtualizacao(atualizado, professorId, "Programa atualizado");

        return atualizado;
    }

    // Finalizar programa (SEM A VALIDAÇÃO DE BIBLIOGRAFIA)
    public Programa finalizarPrograma(Integer id, Integer professorId) {
        Programa programa = buscarPorId(id);


        programa.setFinalizado(true);
        programa.setDataFinalizacao(LocalDateTime.now());
        programa.setAtualizadoPorProfessorId(professorId);
        Programa finalizado = programaRepository.save(programa);

        historicoService.registrarFinalizacao(finalizado, professorId);

        return finalizado;
    }

    // Inativar (sem mudança)
    public void inativarPrograma(Integer id) {
        Programa programa = buscarPorId(id);
        programa.setAtivo(false);
        programaRepository.save(programa);
    }
}
