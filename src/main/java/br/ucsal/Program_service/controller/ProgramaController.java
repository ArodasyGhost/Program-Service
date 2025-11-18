package br.ucsal.Program_service.controller;

import br.ucsal.Program_service.dto.ProgramaDTO;
import br.ucsal.Program_service.model.entity.Programa;

import br.ucsal.Program_service.service.ProgramaService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programas")
@CrossOrigin(origins = "*") // Mantenha se necessário
public class ProgramaController {

    private final ProgramaService programaService;
    // private final ProfessorRepository professorRepository; // <-- REMOVIDO

    // Construtor limpo
    public ProgramaController(ProgramaService programaService) {
        this.programaService = programaService;
    }

    @PostMapping
    // @PreAuthorize(...) // <-- REMOVIDO (Segurança será no Gateway)
    public ResponseEntity<Programa> criar(@RequestBody ProgramaDTO dto) {
        Programa programaCriado = programaService.criarPrograma(dto);
        return ResponseEntity.ok(programaCriado);
    }

    @GetMapping
    // @PreAuthorize(...) // <-- REMOVIDO
    public ResponseEntity<List<Programa>> listar() {
        return ResponseEntity.ok(programaService.listarProgramas());
    }

    // Endpoint para buscar por ID (útil para o Orquestrador)
    @GetMapping("/{id}")
    public ResponseEntity<Programa> buscarPorId(@PathVariable Integer id) {
        Programa programa = programaService.buscarPorId(id);
        return ResponseEntity.ok(programa);
    }

    @PutMapping("/{id}")
    // @PreAuthorize(...) // <-- REMOVIDO
    public ResponseEntity<Programa> atualizar(@PathVariable Integer id,
                                              @RequestBody ProgramaDTO dto,
                                              @RequestParam Integer professorId) {
        // ANTES: buscava o professor no repositório
        // AGORA: Passamos o ID (Integer) direto para o service
        Programa atualizado = programaService.atualizarPrograma(id, dto, professorId);
        return ResponseEntity.ok(atualizado);
    }

    @PutMapping("/{id}/finalizar")
    // @PreAuthorize(...) // <-- REMOVIDO
    public ResponseEntity<Programa> finalizar(@PathVariable Integer id,
                                              @RequestParam Integer professorId) {
        // ANTES: buscava o professor
        // AGORA: Passamos o ID (Integer) direto para o service
        Programa finalizado = programaService.finalizarPrograma(id, professorId);
        return ResponseEntity.ok(finalizado);
    }

    @PutMapping("/{id}/inativar")
    // @PreAuthorize(...) // <-- REMOVIDO
    public ResponseEntity<Void> inativar(@PathVariable Integer id) {
        programaService.inativarPrograma(id);
        return ResponseEntity.noContent().build();
    }
}